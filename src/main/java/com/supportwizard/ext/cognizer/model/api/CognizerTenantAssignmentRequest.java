package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerTenantAssignmentRequest implements Serializable {
    private String orgId;
    private String orderId;
    private boolean isReassignment;

    public CognizerTenantAssignmentRequest() {
    }

    public CognizerTenantAssignmentRequest(String orgId, String orderId, boolean isReassignment) {
        this.orgId = orgId;
        this.orderId = orderId;
        this.isReassignment = isReassignment;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getOrderId() {
        return orderId;
    }

    public boolean isReassignment() {
        return isReassignment;
    }
}
