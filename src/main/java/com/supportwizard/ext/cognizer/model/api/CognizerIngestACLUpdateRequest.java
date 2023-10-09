package com.supportwizard.ext.cognizer.model.api;

import com.supportwizard.ext.cognizer.model.ContractDataWithACL;

import java.util.Arrays;

public class CognizerIngestACLUpdateRequest extends CognizerIngestRequest {
  private final Long eventCreatedTimestamp;
  private final CognizerACL[] aclList;

  public CognizerIngestACLUpdateRequest(ContractDataWithACL contractDataWithACL, String orgId, String contractId, Long eventCreatedTimestamp, CognizerACL[] aclList) {
    super(orgId, contractId, contractDataWithACL);
    this.eventCreatedTimestamp = eventCreatedTimestamp;
    this.aclList = aclList;
  }

  public Long getEventCreatedTimestamp() {
    return eventCreatedTimestamp;
  }

  public CognizerACL[] getAclList() {
    return aclList;
  }

  @Override
  public String toString() {
    return "CognizerIngestACLUpdateRequest{" +
      "orgId='" + orgId + '\'' +
      ", contractId='" + contractId + '\'' +
      ", eventCreatedTimestamp=" + eventCreatedTimestamp +
      ", aclList=" + Arrays.toString(aclList) +
      '}';
  }
}
