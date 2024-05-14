//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer;

import com.supportwizard.actions2.interfaces.ExternalScript;
import com.supportwizard.actions2.interfaces.ScriptActionException;
import com.supportwizard.actions2.interfaces.ScriptInput;
import com.supportwizard.actions2.interfaces.ScriptOutput;
import com.supportwizard.dictionary.SWChoiceLine;
import com.supportwizard.dictionary.MultichoiceLines;
import com.supportwizard.dictionary.interfaces.*;
import com.supportwizard.dml.SWDataMap;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantCreateResponseV2;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantSKUResponseV2;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantUpdateSKUResponseV2;
import com.supportwizard.ext.cognizer.model.api.CognizerUserAssignmentListResponse;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelper2;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelperImpl2;
import com.supportwizard.ext.cognizer.utils.CognizerUtils2;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import com.supportwizard.seance.Seance;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CognizerOrderLicenseGenActionV2 implements ExternalScript {
    private static final Logger log = Logger.getLogger(CognizerOrderLicenseGenActionV2.class);

    public CognizerOrderLicenseGenActionV2() {
    }

    public ScriptOutput runScript(ScriptInput input) throws ScriptActionException {
        ScriptOutput so = new ScriptOutput(input);
        SWDataMap inputRecord = input.getNewRecord();
        SWDataMap updatedRecord = new SWDataMap(inputRecord.getTableID());
        Seance seance = input.getUserSeance();
        so.setRecord(updatedRecord);
        String baseUrl = (String) inputRecord.get("cognizer_setting_base_url");
        String xapiKey = (String) inputRecord.get("cognizer_setting_xapi_key");
        String adminUsername = (String) inputRecord.get("cognizer_setting_admin_username");
        String adminPassword = (String) inputRecord.get("cognizer_setting_admin_password");
        String tenantType = ((SWChoiceLine) inputRecord.get("license_type")).getText();
        String encode = ((SWChoiceLine) inputRecord.get("encode")).getText();
        if ("Conversational AI".equalsIgnoreCase(tenantType)) {
            if (encode.equals("True")) {
                tenantType = "C";
            } else {
                tenantType = "ICS_PLUS";
            }
        }
        if ("AI Platform".equalsIgnoreCase(tenantType)) {
            if (encode.equals("True")) {
                tenantType = "P";
            } else {
                tenantType = "ICS_PLUS_EXTENDED";
            }
        }

        String privacyMode = ((SWChoiceLine) inputRecord.get("privacy_mode")).getText();
        String kbname = (String) inputRecord.get("kb_name");
        String hostname = (String) inputRecord.get("server_hostname");
        String ipaddress = (String) inputRecord.get("ip_address");
        String orgId = (String) inputRecord.get("tenant_org_id");
        String tenantDomain = (String) inputRecord.get("tenant_domain");
        String tenantAdminUserId = (String) inputRecord.get("tenant_admin_userid");
        Timestamp expirationDate = (Timestamp) inputRecord.get("tenant_expiration_date");
        Timestamp assignedDate = (Timestamp) inputRecord.get("tenant_assigned_date");
        Long expirationTimeMin = (expirationDate.getTime() - assignedDate.getTime()) / 60000L;
        Long licCount = (Long) inputRecord.get("tenant_no_of_subscriptions");
        MultichoiceLines productSku = (MultichoiceLines) inputRecord.get("sku");
        String[] productSkuList = null;
        if (productSku != null) {
            Object[] skuList = productSku.toArray();
            System.out.println("skuList=" + skuList);
            productSkuList = new String[skuList.length];
            for (int i = 0; i < skuList.length; i++) {
                String skuLabel = ((SWChoiceLine) skuList[i]).getText();
                productSkuList[i] = skuLabel.substring(skuLabel.indexOf("(") + 1, skuLabel.indexOf(")"));
            }
            System.out.println("productSkuList=" + productSkuList);
        }
        CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
        try {
            CognizerHttpHelper2 httpHelper = new CognizerHttpHelperImpl2(baseUrl, xapiKey, httpClient);

            if (productSku != null) {
                CognizerTenantUpdateSKUResponseV2 resultUpdateSKU = httpHelper.updateTenantSKUV2(adminUsername, adminPassword, orgId, productSkuList);
                System.out.println("resultUpdateSKU=" + resultUpdateSKU);
                if (resultUpdateSKU.getErrorCode() != null) {
                    throw new RuntimeException(resultUpdateSKU.getErrorCode() + " - " + resultUpdateSKU.getDescription());
                }
            }

            // get actual SKUs
            CognizerTenantSKUResponseV2 resultSKU = httpHelper.getTenantSKUV2(adminUsername, adminPassword, orgId);
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

            String licenseKey = CognizerUtils2.generateAiHubLicenseKey(encode.equals("True"), baseUrl, xapiKey, orgId, tenantAdminUserId, expirationDate.getTime(), tenantType, privacyMode, licCount,
                    hostname, ipaddress, expirationTimeMin, assignedDate, kbname, input.getUserSeance(), tenantDomain, productSkuList);

            updatedRecord.put("license_key", licenseKey);
        } catch (Exception var37) {
            log.info("Error while Cognizer License generation", var37);
            so.setExitCode(1);
            so.setMessage(var37.getMessage());
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Throwable var45) {
                }
            }
        }

        return so;
    }
}
