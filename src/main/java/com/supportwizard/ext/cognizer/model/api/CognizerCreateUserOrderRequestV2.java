package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerCreateUserOrderRequestV2 implements Serializable {
    private String tenantId;
    private Long noOfSubscriptions;


    public CognizerCreateUserOrderRequestV2() {
    }

    public CognizerCreateUserOrderRequestV2(String tenantId, Long noOfSubscriptions) {
        this.tenantId = tenantId;
        this.noOfSubscriptions = noOfSubscriptions;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getNoOfSubscriptions() {
        return noOfSubscriptions;
    }

    public void setNoOfSubscriptions(Long noOfSubscriptions) {
        this.noOfSubscriptions = noOfSubscriptions;
    }
}
