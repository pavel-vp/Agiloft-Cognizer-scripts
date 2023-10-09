package com.supportwizard.ext.cognizer.model.api;

import com.supportwizard.ext.cognizer.model.ContractDataWithACL;
import org.jetbrains.annotations.NotNull;

public abstract class CognizerIngestRequest extends CognizerBaseRequest {
  protected final String contractId;
  protected final transient ContractDataWithACL contractDataWithACL;

  public CognizerIngestRequest(String orgId, String contractId, @NotNull ContractDataWithACL contractDataWithACL) {
    super(orgId);
    this.contractId = contractId;
    this.contractDataWithACL = contractDataWithACL;
  }

  public String getContractId() {
    return contractId;
  }

  public ContractDataWithACL getContractDataWithACL() {
    return contractDataWithACL;
  }

}
