package com.supportwizard.ext.cognizer.model.api;

import com.supportwizard.ext.cognizer.model.ContractDataWithACL;

public class CognizerIngestContractDeleteRequest extends CognizerIngestRequest {

  public CognizerIngestContractDeleteRequest(ContractDataWithACL contractDataWithACL, String orgId, String contractId) {
    super(orgId, contractId, contractDataWithACL);
  }

  @Override
  public String toString() {
    return "CognizerIngestContractDeleteRequest{" +
      "orgId='" + orgId + '\'' +
      ", contractId='" + contractId + '\'' +
      '}';
  }
}
