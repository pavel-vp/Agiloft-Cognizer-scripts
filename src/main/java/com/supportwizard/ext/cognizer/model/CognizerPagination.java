//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.model;

public class CognizerPagination {
    private final Integer offset;
    private final Integer count;
    private final String questionId;

    public CognizerPagination(Integer offset, Integer count, String questionId) {
        this.offset = offset;
        this.count = count;
        this.questionId = questionId;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public Integer getCount() {
        return this.count;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public String toString() {
        return "CognizerPagination{offset=" + this.offset + ", count=" + this.count + ", questionId='" + this.questionId + '\'' + '}';
    }
}
