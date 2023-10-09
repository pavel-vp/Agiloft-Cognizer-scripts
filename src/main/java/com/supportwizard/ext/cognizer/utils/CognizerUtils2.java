//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.utils;

import com.supportwizard.cachewrappers.SWTableRelatedCachesWrapper;
import com.supportwizard.db.ColumnList;
import com.supportwizard.db.ConditionList;
import com.supportwizard.dictionary.SWColumnValue;
import com.supportwizard.dictionary.interfaces.Table;
import com.supportwizard.dml.DataRetrieverQueryParams;
import com.supportwizard.dml.SWDataMap;
import com.supportwizard.dml.SWDataROI;
import com.supportwizard.dml.DMLConst.PermissionsApplyingMode;
import com.supportwizard.dml.exceptions.DML_DDLAlreadyInProgressException;
import com.supportwizard.dml.exceptions.ExternalTableAccessException;
import com.supportwizard.dml.exceptions.SWDataRetrievalException;
import com.supportwizard.dml.interfaces.SWDataRetriever;
import com.supportwizard.ext.cognizer.KeyGenAiHub;
import com.supportwizard.ext.cognizer.model.AiHubLicense;
import com.supportwizard.functionalities.lock.SWRecordLockedException;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportwizard.utils.Base64;
import com.supportwizard.utils.DirUtils;
import com.supportwizard.seance.Seance;
import com.supportwizard.swlicenses.exceptions.InvalidLicenseActionException;
import com.supportwizard.swlicenses.util.KeyGen;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CognizerUtils2 {
    private static final Logger log = Logger.getLogger(CognizerUtils2.class);

    public CognizerUtils2() {
    }

    public static SWDataMap getCognizerLicenseRecord(Seance seance, Long aiLicenseId) {
        Table configTable = Table.service().findByName(seance.getProjectID(), "ai_license");
        SWColumnValue pkColumnValue = (SWColumnValue)(new SWTableRelatedCachesWrapper()).getTablePKColumnValues(seance, configTable.getID()).iterator().next();
        SWDataRetriever retriever = SWDataRetriever.service();

        try {
            DataRetrieverQueryParams queryParams = new DataRetrieverQueryParams();
            queryParams.setTableID(configTable.getID());
            ColumnList colList = new ColumnList();
            colList.add("tenant_org_id");
            colList.add("license_type");
            colList.add("privacy_mode");
            colList.add("tenant_name");
            colList.add("tenant_admin_email");
            colList.add("tenant_admin_userid");
            colList.add("tenant_domain");
            colList.add("tenant_org_id");
            colList.add("ip_address");
            colList.add("kb_name");
            colList.add("server_hostname");
            colList.add("cognizer_setting_base_url");
            colList.add("cognizer_setting_xapi_key");
            colList.add("cognizer_setting_admin_username");
            colList.add("cognizer_setting_admin_password");
            queryParams.setSelectClause(colList);
            queryParams.setPermissionsApplyingMode(PermissionsApplyingMode.IGNORE);
            ConditionList where = new ConditionList();
            where.add("", pkColumnValue.getDBName(), "", "=", aiLicenseId, "");
            queryParams.setWhereClause(where);
            retriever.setSeance(seance);
            retriever.setQueryParams(queryParams);
            SWDataROI data = null;

            try {
                if (retriever.count() > 0) {
                    data = retriever.get(0);
                }
            } catch (DML_DDLAlreadyInProgressException | ExternalTableAccessException | SWRecordLockedException var14) {
                return null;
            }

            if (data != null) {
                SWDataMap var9;
                try {
                    if (data.size() > 0) {
                        var9 = data.getRowAsMap(0);
                        return var9;
                    }

                    log.error("There are no records in ai_license");
                    var9 = null;
                } finally {
                    data.dispose();
                }

                return var9;
            }
        } catch (SWDataRetrievalException var16) {
            log.error("Error during getting  ai_license", var16);
        }

        return null;
    }

    public static String generateAiHubLicenseKey(boolean encode, String baseUrl, String xapiKey, String orgId, String tenantAdminUserId, Long expirationDate, String tenantType, String privacyMode, Long qty,
                                                 String hostname, String ipaddress, Long expirationTimeMin, Timestamp assignedDate, String kbname, Seance seance) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy", java.util.Locale.US);
        AiHubLicense aiHubLicense = new AiHubLicense(baseUrl, xapiKey, orgId, tenantAdminUserId, "EmpowerGenius2022", expirationDate, tenantType, privacyMode, qty);

        String tmp = null;
        if (encode) {
            tmp = tenantType + AiHubLicenseUtil2.encrypt(new String(Base64.encode(objectMapper.writeValueAsBytes(aiHubLicense))), orgId);
        } else {
            tmp = new String(Base64.encode(objectMapper.writeValueAsBytes(aiHubLicense)));
        }
        String language = "/C" + tmp;

        String licenseString = "V999" + "/H" + hostname + "/I" + ipaddress + "/A" + expirationTimeMin + "/B" + df.format(assignedDate) + "/E" + df.format(expirationDate) + "/W" + qty + language;
        licenseString += "/K" + kbname;
        String licenseKey = licenseString + "/L" + KeyGenAiHub.getLicenseKey(licenseString, DirUtils.getScriptDirectory(seance) + "/private_key.txt");
        return licenseKey;
    }

}
