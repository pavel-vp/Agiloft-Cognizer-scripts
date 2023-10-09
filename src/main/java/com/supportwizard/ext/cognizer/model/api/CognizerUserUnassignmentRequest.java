package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerUserUnassignmentRequest implements Serializable {
  private String orgId;
  private String userId;

  public CognizerUserUnassignmentRequest() {
  }

  public CognizerUserUnassignmentRequest(String orgId, String userId) {
    this.orgId = orgId;
    this.userId = userId;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getUserId() {
    return userId;
  }
}
