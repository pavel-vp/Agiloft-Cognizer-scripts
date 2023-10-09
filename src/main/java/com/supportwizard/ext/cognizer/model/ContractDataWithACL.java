//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class ContractDataWithACL {
    private final Long attachmentId;
    private final Timestamp attachmentModificationDate;
    private final String[] users;
    private Long recordId;
    private final Integer triesCount;
    private final String status;
    private final String actionType;
    private final String cognizerErrorCode;

    public ContractDataWithACL(Long attachmentId, Timestamp attachmentModificationDate, String[] users, Long recordId, Integer triesCount, String status, String actionType, String cognizerErrorCode) {
        this.attachmentId = attachmentId;
        this.attachmentModificationDate = attachmentModificationDate;
        this.users = users;
        this.recordId = recordId;
        this.triesCount = triesCount == null ? 0 : triesCount;
        this.status = status;
        this.actionType = actionType;
        this.cognizerErrorCode = cognizerErrorCode;
    }

    public Long getAttachmentId() {
        return this.attachmentId;
    }

    public Timestamp getAttachmentModificationDate() {
        return this.attachmentModificationDate;
    }

    public String[] getUsers() {
        return this.users;
    }

    public Long getRecordId() {
        return this.recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Integer getTriesCount() {
        return this.triesCount;
    }

    public String getStatus() {
        return this.status;
    }

    public String getActionType() {
        return this.actionType;
    }

    public String getCognizerErrorCode() {
        return this.cognizerErrorCode;
    }

    public String toString() {
        return "ContractDataWithACL{attachmentId=" + this.attachmentId + ", attachmentModificationDate=" + this.attachmentModificationDate + ", users=" + Arrays.toString(this.users) + ", recordId=" + this.recordId + ", triesCount=" + this.triesCount + ", status=" + this.status + ", actionType=" + this.actionType + ", cognizerErrorCode=" + this.cognizerErrorCode + '}';
    }
}
