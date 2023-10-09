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
import com.supportwizard.dml.SWDataMap;
import com.supportwizard.ext.cognizer.model.api.CognizerOrderCreateResponse;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelper2;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelperImpl2;
import com.supportwizard.ext.cognizer.utils.CognizerUtils2;
import com.supportwizard.seance.Seance;
import java.sql.Timestamp;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class CognizerUserSubscriptionRequestAction implements ExternalScript {
    private static final Logger log = Logger.getLogger(CognizerUserSubscriptionRequestAction.class);

    public CognizerUserSubscriptionRequestAction() {
    }

    public ScriptOutput runScript(ScriptInput input) throws ScriptActionException {
        ScriptOutput so = new ScriptOutput(input);
        SWDataMap inputRecord = input.getNewRecord();
        SWDataMap updatedRecord = new SWDataMap(inputRecord.getTableID());
        Seance seance = input.getUserSeance();
        so.setRecord(updatedRecord);
        Long aiLicenseId = (Long)inputRecord.get("ai_license_id");
        if (aiLicenseId == null) {
            so.setExitCode(1);
            so.setMessage("No AI License record found");
            return so;
        } else {
            SWDataMap licenseRecord = CognizerUtils2.getCognizerLicenseRecord(seance, aiLicenseId);
            if (licenseRecord != null) {
                String baseUrl = (String)licenseRecord.get("cognizer_setting_base_url");
                String xapiKey = (String)licenseRecord.get("cognizer_setting_xapi_key");
                String adminUsername = (String)licenseRecord.get("cognizer_setting_admin_username");
                String adminPassword = (String)licenseRecord.get("cognizer_setting_admin_password");
                String tenantType = ((SWChoiceLine)licenseRecord.get("license_type")).getText();
                if ("Conversational AI".equalsIgnoreCase(tenantType)) {
                    tenantType = "ICS_PLUS";
                }

                if ("AI Platform".equalsIgnoreCase(tenantType)) {
                    tenantType = "ICS_PLUS_EXTENDED";
                }

                String privacyMode = ((SWChoiceLine)licenseRecord.get("privacy_mode")).getText();
                String orgId = (String)licenseRecord.get("tenant_org_id");
                Long noOfSubscription = (Long)inputRecord.get("no_of_subscriptions");

                try {
                    CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
                    Throwable var17 = null;

                    try {
                        CognizerHttpHelper2 httpHelper = new CognizerHttpHelperImpl2(baseUrl, xapiKey, httpClient);
                        CognizerOrderCreateResponse resultOrder = httpHelper.createOrder(adminUsername, adminPassword, tenantType, privacyMode, "USER_ORDER", noOfSubscription);
                        String orderId = resultOrder.getOrderId();
                        Timestamp orderDate = new Timestamp(resultOrder.getOrderDate());
                        Timestamp expirationDate = new Timestamp(resultOrder.getExpirationDate());
                        log.info("Cognizer User Order created: " + orderId);
                        httpHelper.reserveUserSubscription(adminUsername, adminPassword, orderId, orgId, noOfSubscription);
                        log.info("Reserved User Subscriptions to tenant");
                        updatedRecord.put("user_order_id", orderId);
                        updatedRecord.put("order_date", orderDate);
                        updatedRecord.put("expiration_date", expirationDate);
                    } catch (Throwable var31) {
                        var17 = var31;
                        throw var31;
                    } finally {
                        if (httpClient != null) {
                            if (var17 != null) {
                                try {
                                    httpClient.close();
                                } catch (Throwable var30) {
                                    var17.addSuppressed(var30);
                                }
                            } else {
                                httpClient.close();
                            }
                        }

                    }
                } catch (Exception var33) {
                    log.info("Error while Cognizer Tenant creation", var33);
                    so.setExitCode(1);
                    so.setMessage(var33.getMessage());
                }
            } else {
                so.setExitCode(1);
                so.setMessage("No Cognizer configuration or AI License record found");
            }

            return so;
        }
    }
}
