package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerTenantUpdateSKURequestV2 implements Serializable {
    private String tenantId;
    private String[] productSkuList;

    public CognizerTenantUpdateSKURequestV2() {
    }

    public CognizerTenantUpdateSKURequestV2(String tenantId, String[] productSkuList) {
        this.tenantId = tenantId;
        this.productSkuList = productSkuList;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String[] getProductSkuList() {
        return productSkuList;
    }
}
