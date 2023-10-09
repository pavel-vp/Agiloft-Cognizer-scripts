package com.supportwizard.ext.cognizer.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="mailto:hoang.minhto@agiloft.com">Hoang To</a>
 */
public class CognizerLCPPBatchStatusResponse extends CognizerResponse {

  @JsonProperty("completedCount")
  private int completedCount;

  @JsonProperty("inProgressCount")
  private int inProgressCount = -1;

  @JsonProperty("failedCount")
  private int failedCount;

  public CognizerLCPPBatchStatusResponse() {
  }

  public int getCompletedCount() {
    return completedCount;
  }

  public int getInProgressCount() {
    return inProgressCount;
  }

  public int getFailedCount() {
    return failedCount;
  }

}