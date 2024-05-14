package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerTenantCreateRequestV2 implements Serializable {
    private String tenantName;
    private String tenantType;
    private String privacyMode;
    private String tenantAdminEmail;
    private String tenantDomain;
    private String[] productSkuList;
    private int expiryMonths;

    public CognizerTenantCreateRequestV2() {
    }

    public CognizerTenantCreateRequestV2(String tenantName, String tenantType, String privacyMode, String tenantAdminEmail, String tenantDomain, String[] productSkuList, int expiryMonths) {
        this.tenantName = tenantName;
        this.tenantType = tenantType;
        this.privacyMode = privacyMode;
        this.tenantAdminEmail = tenantAdminEmail;
        this.tenantDomain = tenantDomain;
        this.productSkuList = productSkuList;
        this.expiryMonths = expiryMonths;
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

    public String[] getProductSkuList() {
        return productSkuList;
    }

    public void setProductSkuList(String[] productSkuList) {
        this.productSkuList = productSkuList;
    }

    public int getExpiryMonths() {
        return expiryMonths;
    }

    public void setExpiryMonths(int expiryMonths) {
        this.expiryMonths = expiryMonths;
    }
}
