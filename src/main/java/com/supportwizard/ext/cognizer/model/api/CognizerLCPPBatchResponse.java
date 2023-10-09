package com.supportwizard.ext.cognizer.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="mailto:hoang.minhto@agiloft.com">Hoang To</a>
 */
public class CognizerLCPPBatchResponse extends CognizerResponse {

  @JsonProperty("batchId")
  private String batchId;

  @JsonProperty("createdTimeStamp")
  private long createdTimeStamp;

  public CognizerLCPPBatchResponse() {
  }

  public String getBatchId() {
    return batchId;
  }

  public long getCreatedTimeStamp() {
    return createdTimeStamp;
  }
}