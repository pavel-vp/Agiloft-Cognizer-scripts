//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.model;

import java.io.Serializable;
import java.util.List;

public class CognizerQNAResponseData implements Serializable {
    private String answer;
    private Integer confidence;
    private List<CognizerQNAResponseDataDetail> answerDetail;

    public CognizerQNAResponseData() {
    }

    public void setConfidence(int n) {
        this.confidence = n;
    }

    public void setAnswer(String aAnswer) {
        this.answer = aAnswer;
    }

    public String getAnswer() {
        return this.answer;
    }

    public Integer getConfidence() {
        return this.confidence;
    }

    public List<CognizerQNAResponseDataDetail> getAnswerDetail() {
        return this.answerDetail;
    }
}
