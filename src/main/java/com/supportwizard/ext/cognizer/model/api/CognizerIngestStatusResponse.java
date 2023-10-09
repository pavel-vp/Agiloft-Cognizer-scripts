package com.supportwizard.ext.cognizer.model.api;

import java.util.List;

public class CognizerIngestStatusResponse extends CognizerResponse {
  private String orgId;
  private String contractId;
  private List<String> aclList;
  private String ingestionStatus;
  private String error;
  private String errorCode;
  private String errorDescription;

  public CognizerIngestStatusResponse() {}

  public String getOrgId() {
    return orgId;
  }

  public String getContractId() {
    return contractId;
  }

  public List<String> getAclList() {
    return aclList;
  }

  public String getIngestionStatus() {
    return ingestionStatus;
  }

  public String getError() {
    return error;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorDescription() {
    return errorDescription;
  }
}
