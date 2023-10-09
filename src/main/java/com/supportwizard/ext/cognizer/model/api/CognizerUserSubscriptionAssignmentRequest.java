package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerUserSubscriptionAssignmentRequest implements Serializable {
  private String orgId;
  private String orderId;
  private Long noOfSubscriptions;

  public CognizerUserSubscriptionAssignmentRequest() {
  }

  public CognizerUserSubscriptionAssignmentRequest(String orgId, String orderId, Long noOfSubscriptions) {
    this.orgId = orgId;
    this.orderId = orderId;
    this.noOfSubscriptions = noOfSubscriptions;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getOrderId() {
    return orderId;
  }

  public Long getNoOfSubscriptions() {
    return noOfSubscriptions;
  }
}
