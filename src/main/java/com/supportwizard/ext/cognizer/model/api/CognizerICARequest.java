package com.supportwizard.ext.cognizer.model.api;

/**
 * @author <a href="mailto:hoang.minhto@agiloft.com">Hoang To</a>
 */
public class CognizerICARequest extends CognizerBaseRequest {

  protected final String contractId;

  protected final String filePath;

  public CognizerICARequest(String orgId, String contractId, String filePath) {
    super(orgId);
    this.contractId = contractId;
    this.filePath = filePath;
  }

  public String getContractId() {
    return contractId;
  }

  public String getFilePath() {
    return filePath;
  }
}
