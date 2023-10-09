//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.model;

public class CognizerIngestFileRequest {
    private final String orgId;
    private final String contractId;
    private final String filePath;
    private final String type;

    public CognizerIngestFileRequest(String orgId, String contractId, String filePath, String type) {
        this.orgId = orgId;
        this.contractId = contractId;
        this.filePath = filePath;
        this.type = type;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public String getContractId() {
        return this.contractId;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getType() {
        return this.type;
    }
}
