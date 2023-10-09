//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.utils;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class CognizerResponseException extends Exception {
    private final int httpStatusCode;
    private String cognizerErrorCode;
    private String cognizerErrorMessage;

    public CognizerResponseException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;

        try {
            JSONObject errorRecord = null;
            if (message != null && message.charAt(0) == '[') {
                JSONArray errorJsonArray = (JSONArray)JSONValue.parse(message);
                errorRecord = (JSONObject)((JSONObject)errorJsonArray.get(0));
            } else {
                errorRecord = (JSONObject)((JSONObject)JSONValue.parse(message));
            }

            this.cognizerErrorCode = (String)errorRecord.get("errorCode");
            this.cognizerErrorMessage = (String)errorRecord.get("description");
        } catch (Exception var5) {
            this.cognizerErrorCode = null;
            this.cognizerErrorMessage = message;
        }

    }

    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public String getCognizerErrorCode() {
        return this.cognizerErrorCode;
    }

    public String getCognizerErrorMessage() {
        return this.cognizerErrorMessage;
    }
}
