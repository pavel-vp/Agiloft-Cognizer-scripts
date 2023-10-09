package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerOrderCreateRequest implements Serializable {
  private String tenantType;
  private String privacyMode;
  private String orderType;
  private Long noOfSubscriptions;

  public CognizerOrderCreateRequest() {
  }

  public CognizerOrderCreateRequest(String tenantType, String privacyMode, String orderType, Long noOfSubscriptions) {
    this.tenantType = tenantType;
    this.privacyMode = privacyMode;
    this.orderType = orderType;
    this.noOfSubscriptions = noOfSubscriptions;
  }

  public String getTenantType() {
    return tenantType;
  }

  public String getPrivacyMode() {
    return privacyMode;
  }

  public String getOrderType() {
    return orderType;
  }

  public Long getNoOfSubscriptions() {
    return noOfSubscriptions;
  }
}
