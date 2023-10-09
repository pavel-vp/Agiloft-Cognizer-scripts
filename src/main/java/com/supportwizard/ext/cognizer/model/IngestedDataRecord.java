//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.model;

public class IngestedDataRecord {
    private final Long attachmentId;
    private final Long recordId;
    private final String status;
    private final Integer statusCheckCount;

    public IngestedDataRecord(Long attachmentId, Long recordId, String status, Integer statusCheckCount) {
        this.attachmentId = attachmentId;
        this.recordId = recordId;
        this.status = status;
        this.statusCheckCount = statusCheckCount;
    }

    public Long getAttachmentId() {
        return this.attachmentId;
    }

    public Long getRecordId() {
        return this.recordId;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getStatusCheckCount() {
        return this.statusCheckCount;
    }

    public String toString() {
        return "IngestedDataRecord{attachmentId=" + this.attachmentId + ", recordId=" + this.recordId + ", status='" + this.status + '\'' + ", statusCheckCount=" + this.statusCheckCount + '}';
    }
}
