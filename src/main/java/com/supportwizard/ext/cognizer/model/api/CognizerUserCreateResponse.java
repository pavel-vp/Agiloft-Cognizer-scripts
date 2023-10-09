package com.supportwizard.ext.cognizer.model.api;

public class CognizerUserCreateResponse extends CognizerResponse {
  private String id;
  private String userId;
  private String email;
  private String fullName;
  private String organisationId;
  private Long addedOn;
  private String status;

  public String getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public String getFullName() {
    return fullName;
  }

  public String getOrganisationId() {
    return organisationId;
  }

  public Long getAddedOn() {
    return addedOn;
  }

  public String getStatus() {
    return status;
  }
}
