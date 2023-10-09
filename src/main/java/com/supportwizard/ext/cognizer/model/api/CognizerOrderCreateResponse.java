package com.supportwizard.ext.cognizer.model.api;

public class CognizerOrderCreateResponse extends CognizerResponse {
  private String orderId;
  private String tenantType;
  private String privacyMode;
  private Long orderDate;
  private Long expirationDate;
  private Long noOfSubscriptions;
  private String orderType;

  public CognizerOrderCreateResponse() {
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

  public Long getOrderDate() {
    return orderDate;
  }

  public Long getExpirationDate() {
    return expirationDate;
  }

  public Long getNoOfSubscriptions() {
    return noOfSubscriptions;
  }

  public String getOrderType() {
    return orderType;
  }
}
