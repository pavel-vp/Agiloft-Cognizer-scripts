package com.supportwizard.ext.cognizer.model.api;

public class CognizerIngestBatchInfoResponse extends CognizerResponse {
  private String batchId;
  private Long createdTimeStamp;

  public CognizerIngestBatchInfoResponse() {
  }

  public CognizerIngestBatchInfoResponse(String batchId, Long createdTimeStamp) {
    this.batchId = batchId;
    this.createdTimeStamp = createdTimeStamp;
  }

  public String getBatchId() {
    return batchId;
  }

  public Long getCreatedTimeStamp() {
    return createdTimeStamp;
  }
}
