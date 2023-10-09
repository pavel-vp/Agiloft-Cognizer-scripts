//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONAware;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public final class EwHttpHelper {
    private final String ewsHost;
    private final String ewsKb;
    private final String ewsUser;
    private final String ewsPassword;

    public EwHttpHelper(String ewsHost, String ewsKb, String ewsUser, String ewsPassword) {
        this.ewsHost = ewsHost;
        this.ewsKb = ewsKb;
        this.ewsUser = ewsUser;
        this.ewsPassword = ewsPassword;
    }

    public Map<String, String> requestForEwSearch(String tableName, String query, Collection<String> fields) {
        Map<String, String> result = new HashMap();

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            Throwable var6 = null;

            try {
                RequestBuilder builder = RequestBuilder.post(this.ewsHost + "/ewws/EWSearch/.json").setCharset(StandardCharsets.UTF_8).addParameter("$KB", this.ewsKb).addParameter("$login", this.ewsUser).addParameter("$password", this.ewsPassword).addParameter("$table", tableName).addParameter("$lang", "en").addParameter("query", query);
                fields.forEach((name) -> {
                    builder.addParameter("field", name);
                });
                HttpUriRequest method = builder.build();
                CloseableHttpResponse response = httpClient.execute(method);
                int resultCode = response.getStatusLine().getStatusCode();
                if (resultCode == 200) {
                    String responseBodyRaw = EntityUtils.toString(response.getEntity());
                    JSONAware res = (JSONAware)((JSONObject)JSONValue.parse(responseBodyRaw)).get("result");
                    if (!(res instanceof JSONArray) || ((JSONArray)res).isEmpty()) {
                        throw new RuntimeException("Error while getting credentials info from " + this.ewsHost + "! Trying to execute query: " + query + " and got the response: " + responseBodyRaw);
                    }

                    Iterator var13 = ((JSONObject)((JSONObject)((JSONArray)res).get(0))).entrySet().iterator();

                    while(var13.hasNext()) {
                        Map.Entry<String, Object> entry = (Map.Entry)var13.next();
                        result.put(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
            } catch (Throwable var23) {
                var6 = var23;
                throw var23;
            } finally {
                if (httpClient != null) {
                    if (var6 != null) {
                        try {
                            httpClient.close();
                        } catch (Throwable var22) {
                            var6.addSuppressed(var22);
                        }
                    } else {
                        httpClient.close();
                    }
                }

            }

            return result;
        } catch (IOException var25) {
            throw new RuntimeException(var25);
        }
    }

    public String requestForEwCreate(String tableName, Map<String, Object> fieldValues) {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            Throwable var4 = null;

            String responseBodyRaw;
            try {
                RequestBuilder builder = RequestBuilder.post(this.ewsHost + "/ewws/EWCreate/.json").setCharset(StandardCharsets.UTF_8).addParameter("$KB", this.ewsKb).addParameter("$login", this.ewsUser).addParameter("$password", this.ewsPassword).addParameter("$table", tableName).addParameter("$lang", "en");
                fieldValues.forEach((key, value) -> {
                    builder.addParameter(key, (String)value);
                });
                HttpUriRequest method = builder.build();
                CloseableHttpResponse response = httpClient.execute(method);
                int resultCode = response.getStatusLine().getStatusCode();
                if (resultCode == 200) {
                    responseBodyRaw = EntityUtils.toString(response.getEntity());
                    Object res = ((JSONObject)JSONValue.parse(responseBodyRaw)).get("result");
                    String var11;
                    if (!(res instanceof Integer)) {
                        var11 = "Error while trying to create a ticket to " + this.ewsHost + "! Response: " + responseBodyRaw;
                        return var11;
                    }

                    var11 = res.toString();
                    return var11;
                }

                responseBodyRaw = "Error while trying to create a ticket to " + this.ewsHost + "! Response: " + response.getStatusLine().getReasonPhrase();
            } catch (Throwable var23) {
                var4 = var23;
                throw var23;
            } finally {
                if (httpClient != null) {
                    if (var4 != null) {
                        try {
                            httpClient.close();
                        } catch (Throwable var22) {
                            var4.addSuppressed(var22);
                        }
                    } else {
                        httpClient.close();
                    }
                }

            }

            return responseBodyRaw;
        } catch (IOException var25) {
            return var25.getLocalizedMessage();
        }
    }
}
