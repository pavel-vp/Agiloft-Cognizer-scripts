package com.supportwizard.ext.cognizer.model.api;

import java.util.ArrayList;
import java.util.List;

public class CognizerIngestContractDeleteBatchRequest extends CognizerBaseRequest {
  final private String batchId;

  final private List<Long> ingestedDataRecords = new ArrayList<>();

  public CognizerIngestContractDeleteBatchRequest(String orgId, String batchId) {
    super(orgId);
    this.batchId = batchId;
  }

  public String getBatchId() {
    return batchId;
  }

  public List<Long> getIngestedDataRecords() {
    return ingestedDataRecords;
  }
}
