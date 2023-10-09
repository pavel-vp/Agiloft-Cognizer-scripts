package com.supportwizard.ext.cognizer.model.api;

import com.enterprisewizard.ws.impl.CognizerRequestType;
import com.supportwizard.ext.cognizer.model.CognizerPagination;
import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Pinegin
 */
public class CognizerQNARequest extends CognizerBaseRequest {

  protected final String userId;
  protected final String question;

  @NotNull
  private final String requestType;
  protected final String previousQuestionId;

  @NotNull
  private final CognizerPagination pagination;

  public CognizerQNARequest(String orgId, String userId, String question, @NotNull CognizerRequestType requestType,
                            String previousQuestionId, @NotNull CognizerPagination pagination) {
    super(orgId);
    this.userId = userId;
    this.question = question;
    this.requestType = requestType.name;
    this.previousQuestionId = previousQuestionId;
    this.pagination = pagination;
  }

  public String getUserId() {
    return userId;
  }

  public String getQuestion() {
    return question;
  }

  @NotNull
  public String getRequestType() {
    return requestType;
  }

  public String getPreviousQuestionId() {
    return previousQuestionId;
  }

  @NotNull
  public CognizerPagination getPagination() {
    return pagination;
  }

  @Override
  public String toString() {
    return "CognizerQNARequest{" +
      "userId='" + userId + '\'' +
      ", question='" + question + '\'' +
      ", requestType='" + requestType + '\'' +
      ", previousQuestionId='" + previousQuestionId + '\'' +
      ", pagination=" + pagination +
      ", orgId='" + orgId + '\'' +
      '}';
  }
}
