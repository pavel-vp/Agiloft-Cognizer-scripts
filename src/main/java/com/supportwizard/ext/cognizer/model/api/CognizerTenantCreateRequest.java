package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerTenantCreateRequest implements Serializable {
    private String tenantName;
    private String tenantType;
    private String privacyMode;
    private String tenantAdminEmail;
    private String tenantDomain;

    public CognizerTenantCreateRequest() {
    }

    public CognizerTenantCreateRequest(String tenantName, String tenantType, String privacyMode, String tenantAdminEmail, String tenantDomain) {
        this.tenantName = tenantName;
        this.tenantType = tenantType;
        this.privacyMode = privacyMode;
        this.tenantAdminEmail = tenantAdminEmail;
        this.tenantDomain = tenantDomain;
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

    public String getTenantAdminEmail() {
        return tenantAdminEmail;
    }

    public String getTenantDomain() {
        return tenantDomain;
    }
}
