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
import com.supportwizard.dml.SWDataMap;
import com.supportwizard.ext.cognizer.model.AiHubLicense;
import com.supportwizard.ext.cognizer.model.api.CognizerUserAssignmentListResponse;
import com.supportwizard.ext.cognizer.utils.AiHubLicenseUtil2;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelper2;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelperImpl2;
import com.supportwizard.ext.cognizer.utils.CognizerUtils2;
import com.supportwizard.utils.Base64;
import com.supportwizard.utils.DirUtils;
import com.supportwizard.seance.Seance;
import com.supportwizard.swlicenses.exceptions.InvalidLicenseActionException;
import com.supportwizard.swlicenses.util.KeyGen;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class CognizerOrderLicenseGenAction implements ExternalScript {
    private static final Logger log = Logger.getLogger(CognizerOrderLicenseGenAction.class);

    public CognizerOrderLicenseGenAction() {
    }

    public ScriptOutput runScript(ScriptInput input) throws ScriptActionException {
        ScriptOutput so = new ScriptOutput(input);
        SWDataMap inputRecord = input.getNewRecord();
        SWDataMap updatedRecord = new SWDataMap(inputRecord.getTableID());
        so.setRecord(updatedRecord);
        String baseUrl = (String)inputRecord.get("cognizer_setting_base_url");
        String xapiKey = (String)inputRecord.get("cognizer_setting_xapi_key");
        String tenantType = ((SWChoiceLine)inputRecord.get("license_type")).getText();
        if ("Conversational AI".equalsIgnoreCase(tenantType)) {
            tenantType = "C";
        }

        if ("AI Platform".equalsIgnoreCase(tenantType)) {
            tenantType = "P";
        }

        String privacyMode = ((SWChoiceLine)inputRecord.get("privacy_mode")).getText();
        String kbname = (String)inputRecord.get("kb_name");
        String hostname = (String)inputRecord.get("server_hostname");
        String ipaddress = (String)inputRecord.get("ip_address");
        String orgId = (String)inputRecord.get("tenant_org_id");
        String tenantAdminUserId = (String)inputRecord.get("tenant_admin_userid");
        Timestamp expirationDate = (Timestamp)inputRecord.get("tenant_expiration_date");
        Timestamp assignedDate = (Timestamp)inputRecord.get("tenant_assigned_date");
        Long expirationTimeMin = (expirationDate.getTime() - assignedDate.getTime()) / 60000L;
        String encode = ((SWChoiceLine) inputRecord.get("encode")).getText();

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
            Throwable var18 = null;

            try {
                CognizerHttpHelper2 httpHelper = new CognizerHttpHelperImpl2(baseUrl, xapiKey, httpClient);
                CognizerUserAssignmentListResponse userAssignmentResponse = httpHelper.userAssignmentList(tenantAdminUserId, "EmpowerGenius2022");

                String licenseKey = CognizerUtils2.generateAiHubLicenseKey(encode.equals("True"), baseUrl, xapiKey, orgId, tenantAdminUserId, expirationDate.getTime(), tenantType, privacyMode, userAssignmentResponse.getTotalCount(),
                        hostname, ipaddress, expirationTimeMin, assignedDate, kbname, input.getUserSeance());
/*
                String licenseKey = null;
                ObjectMapper objectMapper = new ObjectMapper();
                SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy", Locale.US);
                AiHubLicense aiHubLicense = new AiHubLicense(baseUrl, xapiKey, orgId, tenantAdminUserId, "EmpowerGenius2022", expirationDate.getTime(), tenantType, privacyMode, userAssignmentResponse.getTotalCount());
                String tmp = "/C";
                if (encode.equalsIgnoreCase("True")) {
                    tmp = tmp + tenantType + "_" + userAssignmentResponse.getTotalCount() + "_" + AiHubLicenseUtil2.encrypt(new String(Base64.encode(objectMapper.writeValueAsBytes(aiHubLicense))), orgId);
                } else {
                    tmp = tmp + new String(Base64.encode(objectMapper.writeValueAsBytes(aiHubLicense)));
                }
                String licenseString = "V999/H" + hostname + "/I" + ipaddress + "/A" + expirationTimeMin + "/B" + df.format(assignedDate) + "/E" + df.format(expirationDate) + tmp;
                licenseString = licenseString + "/K" + kbname;
                licenseKey = licenseString + "/L" + KeyGenAiHub.getLicenseKey(licenseString, DirUtils.getScriptDirectory(input.getUserSeance()) + "/private_key.txt");
*/
                updatedRecord.put("tenant_no_of_subscriptions", userAssignmentResponse.getTotalCount());
                updatedRecord.put("license_key", licenseKey);
            } catch (Throwable var35) {
                var18 = var35;
                throw var35;
            } finally {
                if (httpClient != null) {
                    if (var18 != null) {
                        try {
                            httpClient.close();
                        } catch (Throwable var34) {
                            var18.addSuppressed(var34);
                        }
                    } else {
                        httpClient.close();
                    }
                }

            }
        } catch (Exception var37) {
            log.info("Error while Cognizer License generation", var37);
            so.setExitCode(1);
            so.setMessage(var37.getMessage());
        }

        return so;
    }
}
