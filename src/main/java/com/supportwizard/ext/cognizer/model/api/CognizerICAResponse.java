package com.supportwizard.ext.cognizer.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supportwizard.ext.cognizer.model.ICAExtractedClause;
import com.supportwizard.ext.cognizer.model.ICAExtractedTerm;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:hoang.minhto@agiloft.com">Hoang To</a>
 */
public class CognizerICAResponse extends CognizerResponse {

  @JsonProperty("contractId")
  private String contractId;

  @JsonProperty("softwareVersion")
  private String softwareVersion;

  @JsonProperty("timestamp")
  private long timeStamp;

  @JsonProperty("timeTakenToExtract")
  private long timeTakenToExtract;

  @JsonProperty("extractedClauses")
  private List<ICAExtractedClause> extractedClauses;

  @JsonProperty("extractedTerms")
  private List<ICAExtractedTerm> extractedTerms;

  @JsonProperty("requestId")
  private String requestId;

  public CognizerICAResponse() {
  }

  public String getContractId() {
    return contractId;
  }

  public String getSoftwareVersion() {
    return softwareVersion;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public long getTimeTakenToExtract() {
    return timeTakenToExtract;
  }

  public List<ICAExtractedClause> getExtractedClauses() {
    return Collections.unmodifiableList(extractedClauses);
  }

  public List<ICAExtractedTerm> getExtractedTerms() {
    return Collections.unmodifiableList(extractedTerms);
  }

  public String getRequestId() {
    return requestId;
  }
}
