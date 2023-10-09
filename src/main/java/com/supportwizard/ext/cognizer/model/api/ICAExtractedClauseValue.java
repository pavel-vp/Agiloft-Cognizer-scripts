package com.supportwizard.ext.cognizer.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="mailto:hoang.minhto@agiloft.com">Hoang To</a>
 */
public class ICAExtractedClauseValue {

  @JsonProperty("value")
  private String value;

  @JsonProperty("confidence")
  private Integer confidence;

  public ICAExtractedClauseValue() {
  }

  public String getValue() {
    return value;
  }

  public Integer getConfidence() {
    return confidence;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void setConfidence(int confidence) {
    this.confidence = confidence;
  }

}
