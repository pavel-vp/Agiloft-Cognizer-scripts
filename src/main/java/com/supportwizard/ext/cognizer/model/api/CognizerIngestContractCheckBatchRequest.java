package com.supportwizard.ext.cognizer.model.api;

import com.supportwizard.ext.cognizer.model.IngestedDataRecord;

import java.util.ArrayList;
import java.util.List;

public class CognizerIngestContractCheckBatchRequest extends CognizerBaseRequest {
  final private String batchId;

  final private List<IngestedDataRecord> ingestedDataRecords = new ArrayList<>();

  public CognizerIngestContractCheckBatchRequest(String orgId, String batchId) {
    super(orgId);
    this.batchId = batchId;
  }

  public String getBatchId() {
    return batchId;
  }

  public List<IngestedDataRecord> getIngestedDataRecords() {
    return ingestedDataRecords;
  }
}
