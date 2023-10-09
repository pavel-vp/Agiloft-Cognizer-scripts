package com.supportwizard.ext.cognizer.model.api;

public class CognizerIngestBatchStatusResponse extends CognizerResponse {
  private String contractId;
  private String status;
  private String errorCode;
  private String errorDescription;

  public CognizerIngestBatchStatusResponse() {
  }

  public String getContractId() {
    return contractId;
  }

  public String getStatus() {
    return status;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorDescription() {
    return errorDescription;
  }
}
