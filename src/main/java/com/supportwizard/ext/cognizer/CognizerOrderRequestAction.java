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
import com.supportwizard.ext.cognizer.model.api.CognizerOrderCreateResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantAssignmentResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantCreateResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerUserAssignmentListResponse;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelper2;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelperImpl2;
import com.supportwizard.ext.cognizer.utils.CognizerUtils2;
import com.supportwizard.utils.Base64;
import com.supportwizard.utils.DirUtils;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class CognizerOrderRequestAction implements ExternalScript {
    private static final Logger log = Logger.getLogger(CognizerOrderRequestAction.class);

    public CognizerOrderRequestAction() {
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
        if ("Conversational AI".equalsIgnoreCase(tenantType)) {
            tenantType = "ICS_PLUS";
        }

        if ("AI Platform".equalsIgnoreCase(tenantType)) {
            tenantType = "ICS_PLUS_EXTENDED";
        }

        String privacyMode = ((SWChoiceLine)inputRecord.get("privacy_mode")).getText();
        String tenantName = UUID.randomUUID().toString();
        String tenantDomain = tenantName + ".com";
        String tenantAdminEmail = tenantName + "@" + tenantDomain;
        String kbname = (String)inputRecord.get("kb_name");
        String hostname = (String)inputRecord.get("server_hostname");
        String ipaddress = (String)inputRecord.get("ip_address");
        String encode = ((SWChoiceLine) inputRecord.get("encode")).getText();

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
            Throwable var18 = null;

            try {
                CognizerHttpHelper2 httpHelper = new CognizerHttpHelperImpl2(baseUrl, xapiKey, httpClient);
                CognizerOrderCreateResponse resultOrder = httpHelper.createOrder(adminUsername, adminPassword, tenantType, privacyMode, "TENANT_ORDER", 1L);
                String orderId = resultOrder.getOrderId();
                log.info("Cognizer Tenant Order created: " + orderId);
                CognizerTenantCreateResponse resultTenant = httpHelper.createTenant(adminUsername, adminPassword, tenantType, privacyMode, tenantName, tenantAdminEmail, tenantDomain);
                String orgId = resultTenant.getOrgId();
                String tenantAdminUserId = resultTenant.getTenantAdminUserId();
                log.info("Cognizer Tenant Org created: " + orgId);
                CognizerTenantAssignmentResponse tenantAssignmentResponse = httpHelper.assignTenant(adminUsername, adminPassword, orderId, orgId, false);
                String isExpired = tenantAssignmentResponse.getIsExpired();
                String isActive = tenantAssignmentResponse.getIsActive();
                Timestamp expirationDate = new Timestamp(tenantAssignmentResponse.getExpirationDate());
                Timestamp assignedDate = new Timestamp(tenantAssignmentResponse.getAssignedDate());
                Long expirationTimeMin = (expirationDate.getTime() - assignedDate.getTime()) / 60000L;
                log.info("Cognizer Order assigned to Tenant, expirationDate:" + expirationDate);
                CognizerUserAssignmentListResponse userAssignmentResponse = httpHelper.userAssignmentList(tenantAdminUserId, "EmpowerGenius2022");
                if (encode.equals("True")) {
                    if ("ICS_PLUS".equalsIgnoreCase(tenantType)) {
                        tenantType = "C";
                    }
                    if ("ICS_PLUS_EXTENDED".equalsIgnoreCase(tenantType)) {
                        tenantType = "P";
                    }
                }

                String licenseKey = CognizerUtils2.generateAiHubLicenseKey(encode.equals("True"), baseUrl, xapiKey, orgId, tenantAdminUserId, expirationDate.getTime(), tenantType, privacyMode, userAssignmentResponse.getTotalCount(),
                        hostname, ipaddress, expirationTimeMin, assignedDate, kbname, input.getUserSeance());
/*                String licenseKey = null;
                ObjectMapper objectMapper = new ObjectMapper();
                SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy", Locale.US);
                AiHubLicense aiHubLicense = new AiHubLicense(baseUrl, xapiKey, orgId, tenantAdminUserId, "EmpowerGenius2022", expirationDate.getTime(), tenantType, privacyMode, userAssignmentResponse.getTotalCount());
                String tmp = "/C" + new String(Base64.encode(objectMapper.writeValueAsBytes(aiHubLicense)));
                String licenseString = "V999/H" + hostname + "/I" + ipaddress + "/A" + expirationTimeMin + "/B" + df.format(assignedDate) + "/E" + df.format(expirationDate) + tmp;
                licenseString = licenseString + "/K" + kbname;
                licenseKey = licenseString + "/L" + KeyGenAiHub.getLicenseKey(licenseString, DirUtils.getScriptDirectory(input.getUserSeance()) + "/private_key.txt");
*/
                updatedRecord.put("tenant_name", tenantName);
                updatedRecord.put("tenant_admin_email", tenantAdminEmail);
                updatedRecord.put("tenant_domain", tenantDomain);
                updatedRecord.put("tenant_order_id", orderId);
                updatedRecord.put("tenant_org_id", orgId);
                updatedRecord.put("tenant_admin_userid", tenantAdminUserId);
                updatedRecord.put("tenant_expiration_date", expirationDate);
                updatedRecord.put("tenant_assigned_date", assignedDate);
                updatedRecord.put("tenant_no_of_subscriptions", userAssignmentResponse.getTotalCount());
                updatedRecord.put("license_key", licenseKey);
            } catch (Throwable var46) {
                var18 = var46;
                throw var46;
            } finally {
                if (httpClient != null) {
                    if (var18 != null) {
                        try {
                            httpClient.close();
                        } catch (Throwable var45) {
                            var18.addSuppressed(var45);
                        }
                    } else {
                        httpClient.close();
                    }
                }

            }
        } catch (Exception var48) {
            log.info("Error while Cognizer Tenant creation", var48);
            so.setExitCode(1);
            so.setMessage(var48.getMessage());
        }

        return so;
    }
}
