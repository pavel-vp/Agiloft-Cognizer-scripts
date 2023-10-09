//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.model;

import java.io.Serializable;

public class AiHubLicense implements Serializable {
    private String baseUrl;
    private String xApiKey;
    private String orgId;
    private String tenantAdminUserName;
    private String tenantAdminPassword;
    private Long endDate;
    private String tenantType;
    private String privacyMode;
    private Long noOfSubscription;

    public AiHubLicense() {
    }

    public AiHubLicense(String baseUrl, String xApiKey, String orgId, String tenantAdminUserName, String tenantAdminPassword, Long endDate, String tenantType, String privacyMode, Long noOfSubscription) {
        this.baseUrl = baseUrl;
        this.xApiKey = xApiKey;
        this.orgId = orgId;
        this.tenantAdminUserName = tenantAdminUserName;
        this.tenantAdminPassword = tenantAdminPassword;
        this.endDate = endDate;
        this.tenantType = tenantType;
        this.privacyMode = privacyMode;
        this.noOfSubscription = noOfSubscription;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getxApiKey() {
        return this.xApiKey;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public String getTenantAdminUserName() {
        return this.tenantAdminUserName;
    }

    public String getTenantAdminPassword() {
        return this.tenantAdminPassword;
    }

    public Long getEndDate() {
        return this.endDate;
    }

    public String getTenantType() {
        return this.tenantType;
    }

    public String getPrivacyMode() {
        return this.privacyMode;
    }

    public Long getNoOfSubscription() {
        return this.noOfSubscription;
    }
}
