package com.supportwizard.ext.cognizer.model.api;

import java.io.Serializable;

public class CognizerUserCreateRequest implements Serializable {
  private String userId;
  private String firstName;
  private String lastName;
  private String email;

  public CognizerUserCreateRequest() {
  }

  public CognizerUserCreateRequest(String userId, String firstName, String lastName, String email) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public String getUserId() {
    return userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }
}
