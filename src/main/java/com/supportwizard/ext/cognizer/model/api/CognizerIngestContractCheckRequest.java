package com.supportwizard.ext.cognizer.model.api;

import com.supportwizard.ext.cognizer.model.ContractDataWithACL;

public class CognizerIngestContractCheckRequest extends CognizerIngestRequest {

  public CognizerIngestContractCheckRequest(ContractDataWithACL contractDataWithACL, String orgId, String contractId) {
    super(orgId, contractId, contractDataWithACL);
  }

  @Override
  public String toString() {
    return "CognizerIngestContractCheckRequest{" +
      "orgId='" + orgId + '\'' +
      ", contractId='" + contractId + '\'' +
      '}';
  }
}
