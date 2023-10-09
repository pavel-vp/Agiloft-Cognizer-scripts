package com.supportwizard.ext.cognizer.model.api;

import java.util.ArrayList;
import java.util.List;

public class CognizerIngestContractCreateBatchRequest extends CognizerBaseRequest {
  private List<CognizerIngestContractCreateRequest> createRequests = new ArrayList<>();


  public CognizerIngestContractCreateBatchRequest(String orgId) {
    super(orgId);
  }

  public List<CognizerIngestContractCreateRequest> getCreateRequests() {
    return createRequests;
  }

}
