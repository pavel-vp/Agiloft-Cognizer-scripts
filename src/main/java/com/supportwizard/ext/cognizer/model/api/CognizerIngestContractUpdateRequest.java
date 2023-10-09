package com.supportwizard.ext.cognizer.model.api;

import com.supportwizard.ext.cognizer.model.ContractDataWithACL;

import java.util.Set;

public class CognizerIngestContractUpdateRequest extends CognizerIngestRequest {
  private final String filePath;
  private final Long eventCreatedTimestamp;
  private final Set<String> aclList;

  public CognizerIngestContractUpdateRequest(ContractDataWithACL contractDataWithACL, String orgId, String contractId, String filePath, Long eventCreatedTimestamp, Set<String> aclList) {
    super(orgId, contractId, contractDataWithACL);
    this.filePath = filePath;
    this.eventCreatedTimestamp = eventCreatedTimestamp;
    this.aclList = aclList;
  }

  public String getFilePath() {
    return filePath;
  }

  public Long getEventCreatedTimestamp() {
    return eventCreatedTimestamp;
  }

  public Set<String> getAclList() {
    return aclList;
  }

  @Override
  public String toString() {
    return "CognizerIngestContractUpdateRequest{" +
      "orgId='" + orgId + '\'' +
      ", contractId='" + contractId + '\'' +
      ", filePath='" + filePath + '\'' +
      ", eventCreatedTimestamp=" + eventCreatedTimestamp +
      ", aclList=" + aclList +
      '}';
  }
}
