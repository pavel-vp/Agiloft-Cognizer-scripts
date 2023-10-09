package com.supportwizard.ext.cognizer.model.api;

public class CognizerTenantAssignmentResponse extends CognizerResponse {
    private String orgId;
    private String orderId;
    private String tenantType;
    private String privacyMode;
    private String isExpired;
    private String isActive;
    private Long expirationDate;
    private Long assignedDate;

    public String getOrgId() {
        return orgId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTenantType() {
        return tenantType;
    }

    public String getPrivacyMode() {
        return privacyMode;
    }

    public String getIsExpired() {
        return isExpired;
    }

    public String getIsActive() {
        return isActive;
    }

    public Long getExpirationDate() {
        return expirationDate;
    }

    public Long getAssignedDate() {
        return assignedDate;
    }
}