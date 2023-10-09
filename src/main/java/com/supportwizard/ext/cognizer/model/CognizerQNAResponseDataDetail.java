//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CognizerQNAResponseDataDetail implements Serializable {
    private String contractId;
    private String details;
    private CognizerQNAResponseDataDetailMeta contractMeta;
    private Map<String, Object> agiloftData = new HashMap();

    public CognizerQNAResponseDataDetail() {
    }

    public String getContractId() {
        return this.contractId;
    }

    public String getDetails() {
        return this.details;
    }

    public CognizerQNAResponseDataDetailMeta getContractMeta() {
        return this.contractMeta;
    }

    public Map<String, Object> getAgiloftData() {
        return this.agiloftData;
    }
}
