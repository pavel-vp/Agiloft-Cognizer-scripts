package com.supportwizard.ext.cognizer.model.api;

public abstract class CognizerBaseRequest {
  protected final String orgId;

  protected CognizerBaseRequest(String orgId) {
    this.orgId = orgId;
  }

  public String getOrgId() {
    return orgId;
  }

}
