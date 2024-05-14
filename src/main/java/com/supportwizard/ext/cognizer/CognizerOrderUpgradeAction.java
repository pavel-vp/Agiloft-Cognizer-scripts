//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportwizard.actions2.interfaces.ExternalScript;
import com.supportwizard.actions2.interfaces.ScriptActionException;
import com.supportwizard.actions2.interfaces.ScriptInput;
import com.supportwizard.actions2.interfaces.ScriptOutput;
import com.supportwizard.dictionary.SWChoiceLine;
import com.supportwizard.dictionary.interfaces.Choice;
import com.supportwizard.dictionary.interfaces.ChoiceLine;
import com.supportwizard.dml.SWDataMap;
import com.supportwizard.ext.cognizer.model.api.CognizerUserAssignmentListResponse;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelper2;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelperImpl2;
import com.supportwizard.ext.cognizer.utils.CognizerUtils2;
import com.supportwizard.utils.Base64;
import com.supportwizard.utils.DirUtils;
import java.sql.Timestamp;
import java.util.Iterator;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class CognizerOrderUpgradeAction implements ExternalScript {
    private static final Logger log = Logger.getLogger(CognizerOrderUpgradeAction.class);

    public CognizerOrderUpgradeAction() {
    }

    public ScriptOutput runScript(ScriptInput input) throws ScriptActionException {
        ScriptOutput so = new ScriptOutput(input);
        SWDataMap inputRecord = input.getNewRecord();
        SWDataMap updatedRecord = new SWDataMap(inputRecord.getTableID());
        so.setRecord(updatedRecord);
        String baseUrl = (String)inputRecord.get("cognizer_setting_base_url");
        String xapiKey = (String)inputRecord.get("cognizer_setting_xapi_key");
        String adminUsername = (String)inputRecord.get("cognizer_setting_admin_username");
        String adminPassword = (String)inputRecord.get("cognizer_setting_admin_password");
        String tenantType = ((SWChoiceLine)inputRecord.get("license_type")).getText();
        Choice choiceLicenseType = Choice.service().findByName(input.getProjectID(), "ai_license_type");
        String encode = ((SWChoiceLine) inputRecord.get("encode")).getText();

        SWChoiceLine lineConv = null;
        SWChoiceLine lineDoc = null;
        Iterator var13 = choiceLicenseType.getLines().iterator();

        while(var13.hasNext()) {
            ChoiceLine choiceLine = (ChoiceLine)var13.next();
            if ("Conversational AI".equalsIgnoreCase(choiceLine.getText())) {
                lineConv = choiceLine.getSWChoiceLine();
            } else {
                lineDoc = choiceLine.getSWChoiceLine();
            }
        }

        if (!lineConv.getText().equalsIgnoreCase(tenantType)) {
            throw new RuntimeException("Can't upgrade from tenant type: " + tenantType + ", lineConv.getText()=" + lineConv.getText());
        } else {
            if (encode.equals("True")) {
                tenantType = "P";
            } else {
                tenantType = "ICS_PLUS_EXTENDED";
            }
            String privacyMode = ((SWChoiceLine)inputRecord.get("privacy_mode")).getText();
            String kbname = (String)inputRecord.get("kb_name");
            String hostname = (String)inputRecord.get("server_hostname");
            String ipaddress = (String)inputRecord.get("ip_address");
            String orgId = (String)inputRecord.get("tenant_org_id");
            String tenantDomain = (String)inputRecord.get("tenant_domain");
            String tenantAdminUserId = (String)inputRecord.get("tenant_admin_userid");
            Timestamp expirationDate = (Timestamp)inputRecord.get("tenant_expiration_date");
            Timestamp assignedDate = (Timestamp)inputRecord.get("tenant_assigned_date");
            Long expirationTimeMin = (expirationDate.getTime() - assignedDate.getTime()) / 60000L;

            try {
                CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
                Throwable var23 = null;

                try {
                    CognizerHttpHelper2 httpHelper = new CognizerHttpHelperImpl2(baseUrl, xapiKey, httpClient);
                    httpHelper.tenantUpgrade(adminUsername, adminPassword, orgId);
                    log.info("Cognizer Tenant upgraded successfully");
                    CognizerUserAssignmentListResponse userAssignmentResponse = httpHelper.userAssignmentList(tenantAdminUserId, "EmpowerGenius2022");

                    String licenseKey = CognizerUtils2.generateAiHubLicenseKey(encode.equals("True"), baseUrl, xapiKey, orgId, tenantAdminUserId, expirationDate.getTime(), tenantType, privacyMode, userAssignmentResponse.getTotalCount(),
                            hostname, ipaddress, expirationTimeMin, assignedDate, kbname, input.getUserSeance(), tenantDomain, null);

/*                    String licenseKey = null;
                    ObjectMapper objectMapper = new ObjectMapper();
                    SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy", Locale.US);
                    AiHubLicense aiHubLicense = new AiHubLicense(baseUrl, xapiKey, orgId, tenantAdminUserId, "EmpowerGenius2022", expirationDate.getTime(), tenantType, privacyMode, userAssignmentResponse.getTotalCount());
                    String tmp = "/C" + new String(Base64.encode(objectMapper.writeValueAsBytes(aiHubLicense)));
                    String licenseString = "V999/H" + hostname + "/I" + ipaddress + "/A" + expirationTimeMin + "/B" + df.format(assignedDate) + "/E" + df.format(expirationDate) + tmp;
                    licenseString = licenseString + "/K" + kbname;
                    licenseKey = licenseString + "/L" + KeyGenAiHub.getLicenseKey(licenseString, DirUtils.getScriptDirectory(input.getUserSeance()) + "/private_key.txt");
 */
                    updatedRecord.put("tenant_no_of_subscriptions", userAssignmentResponse.getTotalCount());
                    updatedRecord.put("license_type", lineDoc);
                    updatedRecord.put("license_key", licenseKey);
                } catch (Throwable var40) {
                    var23 = var40;
                    throw var40;
                } finally {
                    if (httpClient != null) {
                        if (var23 != null) {
                            try {
                                httpClient.close();
                            } catch (Throwable var39) {
                                var23.addSuppressed(var39);
                            }
                        } else {
                            httpClient.close();
                        }
                    }

                }
            } catch (Exception var42) {
                log.info("Error while Cognizer Tenant creation", var42);
                so.setExitCode(1);
                so.setMessage(var42.getMessage());
            }

            return so;
        }
    }
}
