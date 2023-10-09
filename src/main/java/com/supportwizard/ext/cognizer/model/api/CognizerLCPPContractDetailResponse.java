package com.supportwizard.ext.cognizer.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supportwizard.ext.cognizer.model.ICAExtractedClause;
import com.supportwizard.ext.cognizer.model.ICAExtractedTerm;

import java.util.List;

/**
 * @author <a href="mailto:hoang.minhto@agiloft.com">Hoang To</a>
 */
public class CognizerLCPPContractDetailResponse extends CognizerResponse {

  @JsonProperty("contractId")
  private String contractId;

  @JsonProperty("ingestionStatus")
  private String ingestionStatus;

  @JsonProperty("ingestedAt")
  private Long ingestedAt;

  @JsonProperty("clauses")
  private List<ICAExtractedClause> extractedClauses;

  @JsonProperty("terms")
  private List<ICAExtractedTerm> extractedTerms;

  public CognizerLCPPContractDetailResponse(){
  }

  public String getContractId() {
    return contractId;
  }

  public String getIngestionStatus() {
    return ingestionStatus;
  }

  public Long getIngestedAt() {
    return ingestedAt;
  }

  public List<ICAExtractedClause> getExtractedClauses() {
    return extractedClauses;
  }

  public List<ICAExtractedTerm> getExtractedTerms() {
    return extractedTerms;
  }
}