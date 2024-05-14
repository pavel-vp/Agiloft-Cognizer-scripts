package com.supportwizard.ext.cognizer.model.api;

public class CognizerTenantSKUResponseV2 extends CognizerResponse {
    private String tenantId;
    private String tenantName;
    private String privacyMode;
    private String tenantAdminUserId;
    private String[] productSkuList;
    private Long expiryDate;

    public String getTenantId() {
        return tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getPrivacyMode() {
        return privacyMode;
    }

    public String getTenantAdminUserId() {
        return tenantAdminUserId;
    }

    public String[] getProductSkuList() {
        return productSkuList;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }
}
