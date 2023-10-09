package com.supportwizard.ext.cognizer.model.api;

import com.enterprisewizard.ws.impl.CognizerResponseType;
import com.supportwizard.ext.cognizer.model.CognizerQNAResponseData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CognizerQNAResponse extends CognizerResponse {
  private String questionId;
  private String orgId;
  private String userId;
  private String question;
  private String answerType;
  private Integer offset;
  private Integer count;
  private Integer totalCount;
  private List<CognizerQNAResponseData> data;
  private String viewAllDocumentsDeepLink;

  public CognizerQNAResponse() {}

  public String getQuestionId() {
    return questionId;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getUserId() {
    return userId;
  }

  public String getQuestion() {
    return question;
  }

  @NotNull
  public CognizerResponseType getAnswerType() {
    return CognizerResponseType.getByName(answerType);
  }

  public Integer getOffset() {
    return offset;
  }

  public Integer getCount() {
    return count;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public List<CognizerQNAResponseData> getData() {
    return data;
  }

  public String getViewAllDocumentsDeepLink() {
    return viewAllDocumentsDeepLink;
  }

  public void setViewAllDocumentsDeepLink(String viewAllDocumentsDeepLink) {
    this.viewAllDocumentsDeepLink = viewAllDocumentsDeepLink;
  }
}
