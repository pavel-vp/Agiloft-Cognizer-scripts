package com.supportwizard.ext.cognizer.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class CognizerUserAssignmentResponse extends CognizerResponse {
  private String orgId;
  private String orgName;
  private String userId;
  private Long activationDate;
  private Long expirationDate;
  private boolean isAutoRenew;

  public String getOrgId() {
    return orgId;
  }

  public String getOrgName() {
    return orgName;
  }

  public String getUserId() {
    return userId;
  }

  public Long getActivationDate() {
    return activationDate;
  }

  public Long getExpirationDate() {
    return expirationDate;
  }

  public boolean isAutoRenew() {
    return isAutoRenew;
  }
}
