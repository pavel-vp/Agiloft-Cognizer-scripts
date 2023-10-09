//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.model;

import java.io.Serializable;
import java.util.List;

public class CognizerQNAResponseDataDetailMeta implements Serializable {
    private List<String> parties;
    private List<String> names;
    private String contractType;
    private Long startDate;
    private Long endDate;
    private Long duration;
    private String governingLaw;
    private List<CognizerQNAResponseDataDetailMetaClause> clauses;

    public CognizerQNAResponseDataDetailMeta() {
    }

    public List<String> getParties() {
        return this.parties;
    }

    public List<String> getNames() {
        return this.names;
    }

    public String getContractType() {
        return this.contractType;
    }

    public Long getStartDate() {
        return this.startDate;
    }

    public Long getEndDate() {
        return this.endDate;
    }

    public Long getDuration() {
        return this.duration;
    }

    public String getGoverningLaw() {
        return this.governingLaw;
    }

    public List<CognizerQNAResponseDataDetailMetaClause> getClauses() {
        return this.clauses;
    }
}
