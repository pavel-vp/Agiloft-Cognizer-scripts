package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerUserAssignmentRequest implements Serializable {
  private String userId;

  public CognizerUserAssignmentRequest() {
  }

  public CognizerUserAssignmentRequest(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }
}
