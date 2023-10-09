package com.supportwizard.ext.cognizer.model.api;

import java.util.Set;

public class CognizerIngestACLUpdateBatchResponse extends CognizerResponse {

  public static class Success extends CognizerResponse {
    private int count = 0;

    public Success() {
    }

    public int getCount() {
      return count;
    }
  }

  public static class Failures extends CognizerResponse {
    private int count = 0;
    private Set<String> contractIds;

    public Failures() {
    }

    public int getCount() {
      return count;
    }
    public Set<String> getContractIds() {
      return contractIds;
    }

  }

  private Success success;
  private Failures failures;

  public CognizerIngestACLUpdateBatchResponse() {
  }

  public CognizerIngestACLUpdateBatchResponse(Success success, Failures failures) {
    this.success = success;
    this.failures = failures;
  }

  public Success getSuccess() {
    return success;
  }

  public Failures getFailures() {
    return failures;
  }

}
