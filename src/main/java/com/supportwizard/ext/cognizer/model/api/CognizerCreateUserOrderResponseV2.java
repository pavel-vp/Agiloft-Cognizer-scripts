package com.supportwizard.ext.cognizer.model.api;

public class CognizerCreateUserOrderResponseV2 extends CognizerResponse {
    private String tenantId;
    private Long noOfSubscriptions;
    private Long orderDate;

    public CognizerCreateUserOrderResponseV2() {
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

    public Long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Long orderDate) {
        this.orderDate = orderDate;
    }
}
