package com.supportwizard.ext.cognizer.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class CognizerTenantUpdateSKUResponseV2 extends CognizerResponse {
    private String tenantId;
    private String[] productSkuList;
    private String errorCode;
    private String description;

    public String getTenantId() {
        return tenantId;
    }

    public String[] getProductSkuList() {
        return productSkuList;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
