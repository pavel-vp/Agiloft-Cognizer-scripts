package com.supportwizard.ext.cognizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportwizard.actions2.interfaces.ExternalScript;
import com.supportwizard.actions2.interfaces.ScriptActionException;
import com.supportwizard.actions2.interfaces.ScriptInput;
import com.supportwizard.actions2.interfaces.ScriptOutput;
import com.supportwizard.dictionary.SWChoiceLine;
import com.supportwizard.dictionary.MultichoiceLines;
import com.supportwizard.dictionary.interfaces.*;
import com.supportwizard.dml.SWDataMap;
import com.supportwizard.ext.cognizer.model.api.*;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelper2;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelperImpl2;
import com.supportwizard.ext.cognizer.utils.CognizerUtils2;
import com.supportwizard.utils.Base64;
import com.supportwizard.utils.DirUtils;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import com.supportwizard.seance.Seance;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class CognizerOrderRequestActionV2 implements ExternalScript {
    private static final Logger log = Logger.getLogger(CognizerOrderRequestActionV2.class);

    public CognizerOrderRequestActionV2() {
    }

    public ScriptOutput runScript(ScriptInput input) throws ScriptActionException {
        ScriptOutput so = new ScriptOutput(input);
        SWDataMap inputRecord = input.getNewRecord();
        SWDataMap updatedRecord = new SWDataMap(inputRecord.getTableID());
        Seance seance = input.getUserSeance();
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

        String privacyMode = ((SWChoiceLine) inputRecord.get("privacy_mode")).getText();
        String tenantName = UUID.randomUUID().toString();
        String tenantDomain = tenantName + ".com";
        String tenantAdminEmail = tenantName + "@" + tenantDomain;
        String kbname = (String) inputRecord.get("kb_name");
        String hostname = (String) inputRecord.get("server_hostname");
        String ipaddress = (String) inputRecord.get("ip_address");
        Long licQty = (Long) inputRecord.get("tenant_no_of_subscriptions");
        String encode = ((SWChoiceLine) inputRecord.get("encode")).getText();
        Integer expiryMonths = (Integer) inputRecord.get("expiry_months");
        if (encode.equals("True")) {
            if ("ICS_PLUS".equalsIgnoreCase(tenantType)) {
                tenantType = "C";
            }
            if ("ICS_PLUS_EXTENDED".equalsIgnoreCase(tenantType)) {
                tenantType = "P";
            }
        }

        try {

            MultichoiceLines productSku = (MultichoiceLines) inputRecord.get("sku");
            if (productSku == null) {
                throw new RuntimeException("SKU must be selected");
            }
            Object[] skuList = productSku.toArray();
            System.out.println("skuList="+skuList);
            String[] productSkuList = new String[skuList.length];
            for (int i=0; i< skuList.length; i++) {
                String skuLabel = ((SWChoiceLine)skuList[i]).getText();
                productSkuList[i] = skuLabel.substring(skuLabel.indexOf("(")+1, skuLabel.indexOf(")"));
            }
            System.out.println("productSkuList="+productSkuList);

            CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
            Throwable var18 = null;

            try {
                CognizerHttpHelper2 httpHelper = new CognizerHttpHelperImpl2(baseUrl, xapiKey, httpClient);
/*
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
*/

                CognizerTenantCreateResponseV2 resultTenant = httpHelper.createTenantV2(adminUsername, adminPassword, tenantType, privacyMode, tenantName, tenantAdminEmail, tenantDomain, productSkuList, expiryMonths);
                String tenantId = resultTenant.getTenantId();
                String tenantAdminUserId = resultTenant.getTenantAdminUserId();
                Date expirationDate = new Timestamp(resultTenant.getExpiryDate());
                Long expirationTimeMin = (long) (expiryMonths * 30 * 24 * 60);
                Timestamp assignedDate = Timestamp.from(Instant.now());
                log.info("Cognizer Tenant Org created: " + tenantId);

                // get actual SKUs
                CognizerTenantSKUResponseV2 resultSKU = httpHelper.getTenantSKUV2(adminUsername, adminPassword, tenantId);
                Choice choice = Choice.service().findByName(seance.getProjectID(), "cognizer_sku");
                List<ChoiceLine> lines = Choice.service().findLinesByChoiceID(choice.getID());
                List<SWChoiceLine> resultSWChoices = new ArrayList<>();
                for (String sku: resultSKU.getProductSkuList()) {
                    // find this choice
                    SWChoiceLine found = null;
                    for (ChoiceLine line : lines) {
                        if (line.getText().contains(sku)) {
                            found = line.getSWChoiceLine();
                            break;
                        }
                    }
                    resultSWChoices.add(found);
                }
                MultichoiceLines productSkuFromServer = new MultichoiceLines(resultSWChoices);
                updatedRecord.put("sku", productSkuFromServer);

                String licenseKey = CognizerUtils2.generateAiHubLicenseKey(encode.equals("True"), baseUrl, xapiKey, tenantId, tenantAdminUserId, expirationDate.getTime(), tenantType, privacyMode, licQty,
                        hostname, ipaddress, expirationTimeMin, assignedDate, kbname, input.getUserSeance(), tenantDomain, productSkuList);

                updatedRecord.put("tenant_name", tenantName);
                updatedRecord.put("tenant_admin_email", tenantAdminEmail);
                updatedRecord.put("tenant_domain", tenantDomain);

                updatedRecord.put("tenant_org_id", tenantId);
                updatedRecord.put("tenant_admin_userid", tenantAdminUserId);

                updatedRecord.put("tenant_expiration_date", expirationDate);
                updatedRecord.put("tenant_assigned_date", assignedDate);
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
