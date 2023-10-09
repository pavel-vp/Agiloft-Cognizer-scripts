package com.supportwizard.ext.cognizer.model.api;

import com.supportwizard.ext.cognizer.model.ContractDataWithACL;

import java.util.Set;

public class CognizerIngestACLResetRequest extends CognizerIngestRequest {
  private final Long eventCreatedTimestamp;
  private final Set<String> aclList;

  public CognizerIngestACLResetRequest(ContractDataWithACL contractDataWithACL, String orgId, String contractId, Long eventCreatedTimestamp, Set<String> aclList) {
    super(orgId, contractId, contractDataWithACL);
    this.eventCreatedTimestamp = eventCreatedTimestamp;
    this.aclList = aclList;
  }

  public Long getEventCreatedTimestamp() {
    return eventCreatedTimestamp;
  }

  public Set<String> getAclList() {
    return aclList;
  }

  @Override
  public String toString() {
    return "CognizerIngestACLResetRequest{" +
      "orgId='" + orgId + '\'' +
      ", contractId='" + contractId + '\'' +
      ", eventCreatedTimestamp=" + eventCreatedTimestamp +
      ", aclList=" + aclList +
      '}';
  }
}
