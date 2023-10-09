package com.supportwizard.ext.cognizer.model.api;

public class CognizerUserAssignmentListResponse extends CognizerResponse {
    private Long totalCount;
    private Long assignedCount;
    private Long unassignedCount;
    private CognizerUserAssignmentResponse[] response;

  public Long getTotalCount() {
    return totalCount;
  }

  public Long getAssignedCount() {
    return assignedCount;
  }

  public Long getUnassignedCount() {
    return unassignedCount;
  }

  public CognizerUserAssignmentResponse[] getResponse() {
    return response;
  }
}
