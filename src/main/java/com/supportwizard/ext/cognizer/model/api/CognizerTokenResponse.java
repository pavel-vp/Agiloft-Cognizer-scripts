package com.supportwizard.ext.cognizer.model.api;

import net.minidev.json.annotate.JsonIgnore;

public class CognizerTokenResponse extends CognizerResponse {
  @JsonIgnore
  private String access_token;
  private long expires_in;
  private long refresh_expires_in;
  private String refresh_token;
  private String token_type;
  private String session_state;
  private String scope;
  private long creationTimestamp;

  public CognizerTokenResponse() {}

  public String getAccess_token() {
    return access_token;
  }

  public long getExpires_in() {
    return expires_in;
  }

  public long getRefresh_expires_in() {
    return refresh_expires_in;
  }

  public String getRefresh_token() {
    return refresh_token;
  }

  public String getToken_type() {
    return token_type;
  }

  public String getSession_state() {
    return session_state;
  }

  public String getScope() {
    return scope;
  }

  public long getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(long creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

}
