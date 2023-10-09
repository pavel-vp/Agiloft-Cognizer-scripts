package com.supportwizard.ext.cognizer.model.api;

import com.supportwizard.ext.cognizer.model.IngestedDataRecord;

import java.util.ArrayList;
import java.util.List;

public class CognizerIngestACLUpdateBatchRequest extends CognizerBaseRequest {
  private final List<String> contractIds = new ArrayList<>();
  private final List<String> aclList;
  private final transient String operationType;
  private final transient List<IngestedDataRecord> ingestedDataRecords = new ArrayList<>();

  public CognizerIngestACLUpdateBatchRequest(String orgId, List<String> aclList, String operationType) {
    super(orgId);
    this.aclList = aclList;
    this.operationType = operationType;
  }

  public List<String> getContractIds() {
    return contractIds;
  }

  public List<String> getAclList() {
    return aclList;
  }

  public String getOperationType() {
    return operationType;
  }

  public List<IngestedDataRecord> getIngestedDataRecords() {
    return ingestedDataRecords;
  }

}
