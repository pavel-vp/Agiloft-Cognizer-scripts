package com.supportwizard.ext.cognizer;

import com.supportwizard.actions2.interfaces.*;
import com.supportwizard.dml.SWDataMap;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelper2;
import com.supportwizard.ext.cognizer.utils.CognizerHttpHelperImpl2;
import com.supportwizard.ext.cognizer.utils.CognizerResponseException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import com.supportwizard.cachewrappers.SWChoiceCacheWrapper;

public class CognizerTenantDeactivateActionV2 implements ExternalScript {
  private static final Logger log = Logger.getLogger(CognizerTenantDeactivateActionV2.class);
  @Override
  public ScriptOutput runScript(ScriptInput input) throws ScriptActionException {
    final ScriptOutput so = new ScriptOutput(input);
    final SWDataMap inputRecord = input.getNewRecord();

    final SWDataMap updatedRecord = new SWDataMap(inputRecord.getTableID());
    so.setRecord(updatedRecord);

    String baseUrl = (String) inputRecord.get("cognizer_setting_base_url");
    String xapiKey = (String) inputRecord.get("cognizer_setting_xapi_key");
    String adminUsername = (String) inputRecord.get("cognizer_setting_admin_username");
    String adminPassword = (String) inputRecord.get("cognizer_setting_admin_password");

    String orgId = (String) inputRecord.get("tenant_org_id");

    try (CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build()) {

      CognizerHttpHelper2 httpHelper = new CognizerHttpHelperImpl2(baseUrl, xapiKey, httpClient);

      // Deactivate tenant
      httpHelper.tenantDeactivationV2(adminUsername, adminPassword, orgId);
      log.info("Cognizer Tenant deactivated successfully");

      updatedRecord.put("license_key", "");
      updatedRecord.put("tenant_deactivated",  new SWChoiceCacheWrapper().getYesChoiceLine(input.getUserSeance()));

    } catch (CognizerResponseException ex) {
      if ("GE-039".equals(ex.getCognizerErrorCode())) {
        log.info("Cognizer Tenant doesn't exist");

        updatedRecord.put("license_key", "");
        updatedRecord.put("tenant_deactivated",  new SWChoiceCacheWrapper().getYesChoiceLine(input.getUserSeance()));
      } else {
        log.info("Error while Cognizer Tenant deactivation", ex);
        so.setExitCode(ExternalScript.BLOCK);
        so.setMessage(ex.getMessage());
      }
    } catch (Exception e) {
      log.info("Error while Cognizer Tenant deactivation", e);
      so.setExitCode(ExternalScript.BLOCK);
      so.setMessage(e.getMessage());
    }

    return so;
  }
}
