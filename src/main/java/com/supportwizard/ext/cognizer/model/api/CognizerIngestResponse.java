package com.supportwizard.ext.cognizer.model.api;

public class CognizerIngestResponse extends CognizerResponse {
  private String contractId;

  public CognizerIngestResponse() {}

  public CognizerIngestResponse(String contractId) {
    this.contractId = contractId;
  }

  public String getContractId() {
    return contractId;
  }
}
