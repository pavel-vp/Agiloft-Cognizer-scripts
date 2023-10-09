package com.supportwizard.ext.cognizer.model.api;

public class CognizerTenantCreateResponse extends CognizerResponse {
    private String orgId;
    private String tenantName;
    private String tenantType;
    private String privacyMode;
    private String tenantAdminUserId;

    public String getOrgId() {
        return orgId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getTenantType() {
        return tenantType;
    }

    public String getPrivacyMode() {
        return privacyMode;
    }

    public String getTenantAdminUserId() {
        return tenantAdminUserId;
    }
}
