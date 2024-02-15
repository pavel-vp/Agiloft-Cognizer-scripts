//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportwizard.ext.cognizer.model.CognizerUser;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestACLResetRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestACLUpdateRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestContractCheckRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestContractCreateRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestContractDeleteRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestContractUpdateRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestStatusResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerOrderCreateRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerOrderCreateResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerQNARequest;
import com.supportwizard.ext.cognizer.model.api.CognizerQNAResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantAssignmentRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantAssignmentResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantCreateRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantCreateResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerTokenResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerUserAssignmentListResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerUserSubscriptionAssignmentRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public final class CognizerHttpHelperImpl2 implements CognizerHttpHelper2 {
    private static final Logger log = Logger.getLogger(CognizerHttpHelperImpl2.class);
    private final String cognizerBaseUrl;
    private final String xApiHeaderKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final ConcurrentHashMap<CognizerUserKey, CognizerTokenResponse> tokens = new ConcurrentHashMap();

    public CognizerHttpHelperImpl2(String aCognizerBaseUrl, String aXApiHeaderKey, HttpClient aHttpClient) {
        this.cognizerBaseUrl = aCognizerBaseUrl;
        this.xApiHeaderKey = aXApiHeaderKey;
        this.httpClient = aHttpClient;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void register(CognizerUser cognizerUser) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        this.sendRequest("POST", this.cognizerBaseUrl + "/" + "register", headers, cognizerUser, (Class)null);
    }

    public CognizerTokenResponse getToken(final String username, final String password) throws Exception {
        log.info("getToken:: start");
        CognizerUserKey userKey = new CognizerUserKey(this.xApiHeaderKey, username, password);
        CognizerTokenResponse existedToken = this.checkAndGetToken(userKey);
        log.info("getToken:: existedToken=" + existedToken);
        if (existedToken != null) {
            return existedToken;
        } else {
            Map<String, String> headers = new HashMap<String, String>() {
                {
                    this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
                    this.put("username", username);
                    this.put("password", password);
                }
            };
            CognizerTokenResponse newToken = (CognizerTokenResponse)this.sendRequest("POST", this.cognizerBaseUrl + "/" + "token", headers, (Object)null, CognizerTokenResponse.class);
            log.info("getToken:: newToken=" + newToken);
            if (newToken == null) {
                throw new RuntimeException("Error retrieving token");
            } else {
                newToken.setCreationTimestamp(System.currentTimeMillis());
                tokens.putIfAbsent(userKey, newToken);
                return newToken;
            }
        }
    }

    private CognizerTokenResponse checkAndGetToken(CognizerUserKey userKey) {
        CognizerTokenResponse existedToken = (CognizerTokenResponse)tokens.get(userKey);
        if (existedToken != null) {
            long currentTimestamp = System.currentTimeMillis();
            if (currentTimestamp > existedToken.getCreationTimestamp() + existedToken.getExpires_in() * 1000L) {
                tokens.remove(userKey);
                existedToken = null;
            }
        }

        return existedToken;
    }

    public CognizerIngestResponse ingestData(CognizerIngestRequest cognizerIngestRequest, String userName, String password) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        CognizerIngestResponse result;
        if (cognizerIngestRequest instanceof CognizerIngestContractCreateRequest) {
            result = (CognizerIngestResponse)this.sendRequestWithAuth("POST", this.cognizerBaseUrl + "/" + "contract", headers, cognizerIngestRequest, CognizerIngestResponse.class, userName, password);
        } else if (cognizerIngestRequest instanceof CognizerIngestContractUpdateRequest) {
            result = (CognizerIngestResponse)this.sendRequestWithAuth("PUT", this.cognizerBaseUrl + "/" + "contract", headers, cognizerIngestRequest, CognizerIngestResponse.class, userName, password);
        } else if (cognizerIngestRequest instanceof CognizerIngestContractDeleteRequest) {
            result = (CognizerIngestResponse)this.sendRequestWithAuth("DELETE", this.cognizerBaseUrl + "/" + "contract" + "/" + cognizerIngestRequest.getOrgId() + "/" + cognizerIngestRequest.getContractId(), headers, (Object)null, CognizerIngestResponse.class, userName, password);
        } else {
            if (!(cognizerIngestRequest instanceof CognizerIngestContractCheckRequest)) {
                throw new RuntimeException("Unknown ingest request:" + cognizerIngestRequest);
            }

            result = (CognizerIngestResponse)this.sendRequestWithAuth("GET", this.cognizerBaseUrl + "/" + "contract" + "/" + cognizerIngestRequest.getOrgId() + "/" + cognizerIngestRequest.getContractId(), headers, (Object)null, CognizerIngestResponse.class, userName, password);
        }

        return result;
    }

    public void sendACL(CognizerIngestRequest cognizerIngestRequest, String userName, String password) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        if (cognizerIngestRequest instanceof CognizerIngestACLResetRequest) {
            this.sendRequestWithAuth("POST", this.cognizerBaseUrl + "/" + "acl", headers, cognizerIngestRequest, (Class)null, userName, password);
        } else if (cognizerIngestRequest instanceof CognizerIngestACLUpdateRequest) {
            this.sendRequestWithAuth("PUT", this.cognizerBaseUrl + "/" + "acl", headers, cognizerIngestRequest, (Class)null, userName, password);
        }

    }

    public CognizerIngestStatusResponse getStatus(String orgId, String composeContractId, String userName, String password) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        return (CognizerIngestStatusResponse)this.sendRequestWithAuth("GET", this.cognizerBaseUrl + "/" + "contract" + "/" + orgId + "/" + composeContractId, headers, (Object)null, CognizerIngestStatusResponse.class, userName, password);
    }

    public CognizerQNAResponse getAnswer(CognizerQNARequest answerRequest, String userName, String password) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        return (CognizerQNAResponse)this.sendRequestWithAuth("POST", this.cognizerBaseUrl + "/" + "contract" + "/" + "answer", headers, answerRequest, CognizerQNAResponse.class, userName, password);
    }

    public CognizerOrderCreateResponse createOrder(String adminUsername, String adminPassword, String tenantType, String privacyMode, String orderType, Long noOfSubscription) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        CognizerOrderCreateRequest request = new CognizerOrderCreateRequest(tenantType, privacyMode, orderType, noOfSubscription);
        return (CognizerOrderCreateResponse)this.sendRequestWithAuth("POST", this.cognizerBaseUrl + "/" + "order", headers, request, CognizerOrderCreateResponse.class, adminUsername, adminPassword);
    }

    public CognizerTenantCreateResponse createTenant(String adminUsername, String adminPassword, String tenantType, String privacyMode, String tenantName, String tenantAdminEmail, String tenantDomain) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        CognizerTenantCreateRequest request = new CognizerTenantCreateRequest(tenantName, tenantType, privacyMode, tenantAdminEmail, tenantDomain);
        return (CognizerTenantCreateResponse)this.sendRequestWithAuth("POST", this.cognizerBaseUrl + "/" + "organisation", headers, request, CognizerTenantCreateResponse.class, adminUsername, adminPassword);
    }

    public CognizerTenantAssignmentResponse assignTenant(String adminUsername, String adminPassword, String orderId, String orgId, boolean isReassignment) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        CognizerTenantAssignmentRequest request = new CognizerTenantAssignmentRequest(orgId, orderId, isReassignment);
        this.sendRequestWithAuth("POST", this.cognizerBaseUrl + "/" + "tenant/assignment", headers, request, (Class)null, adminUsername, adminPassword);
        return (CognizerTenantAssignmentResponse)this.sendRequestWithAuth("GET", this.cognizerBaseUrl + "/" + "tenant/assignment" + "?orgId=" + orgId, headers, (Object)null, CognizerTenantAssignmentResponse.class, adminUsername, adminPassword);
    }

    public void tenantUpgrade(String adminUsername, String adminPassword, String orgId) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        this.sendRequestWithAuth("POST", this.cognizerBaseUrl + "/" + "tenant/upgrade" + "?orgId=" + orgId, headers, (Object)null, (Class)null, adminUsername, adminPassword);
    }

    public void tenantDeactivation(String adminUsername, String adminPassword, String orgId) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        this.sendRequestWithAuth("DELETE", this.cognizerBaseUrl + "/" + "organisation" + "/" + orgId+"?removeData=true", headers, (Object)null, (Class)null, adminUsername, adminPassword);
    }

    public void reserveUserSubscription(String adminUsername, String adminPassword, String orderId, String orgId, Long noOfSubscription) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        CognizerUserSubscriptionAssignmentRequest request = new CognizerUserSubscriptionAssignmentRequest(orgId, orderId, noOfSubscription);
        this.sendRequestWithAuth("POST", this.cognizerBaseUrl + "/" + "tenant/assignment/reserve-subscriptions", headers, request, (Class)null, adminUsername, adminPassword);
    }

    public CognizerUserAssignmentListResponse userAssignmentList(String adminUsername, String adminPassword) throws Exception {
        Map<String, String> headers = new HashMap<String, String>() {
            {
                this.put("x-api-key", CognizerHttpHelperImpl2.this.xApiHeaderKey);
            }
        };
        return (CognizerUserAssignmentListResponse)this.sendRequestWithAuth("GET", this.cognizerBaseUrl + "/" + "user/assignment/list", headers, (Object)null, CognizerUserAssignmentListResponse.class, adminUsername, adminPassword);
    }

    private CognizerTokenResponse getTokenEnforced(String username, String password) throws Exception {
        log.info("getTokenEnforced:: start");
        tokens.remove(new CognizerUserKey(this.xApiHeaderKey, username, password));
        return this.getToken(username, password);
    }

    private <R extends CognizerResponse> R sendRequestWithAuth(String method, String url, Map<String, String> headers, Object body, Class<R> expectedResponseClass, String userName, String password) throws Exception {
        log.info("sendRequestWithAuth:: start");
        CognizerTokenResponse token = this.getToken(userName, password);
        R res = null;

        try {
            headers.put("Authorization", "Bearer " + token.getAccess_token());
            res = this.sendRequest(method, url, headers, body, expectedResponseClass);
        } catch (CognizerResponseException var12) {
            if (var12.getHttpStatusCode() != 403) {
                throw var12;
            }

            CognizerTokenResponse tokenResponseAnother = this.getTokenEnforced(userName, password);
            headers.put("Authorization", "Bearer " + tokenResponseAnother.getAccess_token());
            log.info("sendRequestWithAuth:: token2=" + tokenResponseAnother.getAccess_token());
            res = this.sendRequest(method, url, headers, body, expectedResponseClass);
        }

        return res;
    }

    private <R extends CognizerResponse> R sendRequest(String method, String url, Map<String, String> headers, Object body, Class<R> expectedResponseClass) throws Exception {
        StringEntity postEntity;
        if (body == null) {
            postEntity = null;
        } else {
            String objectAsString = (new ObjectMapper()).writeValueAsString(body);
            log.info("sending request to Cognizer API: objectAsString=" + objectAsString);
            postEntity = new StringEntity(objectAsString, StandardCharsets.UTF_8);
            postEntity.setContentType("application/json");
        }

        log.info("sending request to Cognizer API: url=" + url + ", headers=" + headers + ", body=" + postEntity);
        RequestBuilder request;
        if ("POST".equals(method)) {
            request = RequestBuilder.post(url);
        } else if ("PUT".equals(method)) {
            request = RequestBuilder.put(url);
        } else if ("DELETE".equals(method)) {
            request = RequestBuilder.delete(url);
        } else {
            if (!"GET".equals(method)) {
                throw new IllegalStateException("Unexpected value: " + method);
            }

            request = RequestBuilder.get(url);
        }

        headers.forEach(request::addHeader);
        if (postEntity != null) {
            request.setEntity(postEntity);
        }

        HttpResponse response = this.httpClient.execute(request.build());
        int resultCode = response.getStatusLine().getStatusCode();
        String result = EntityUtils.toString(response.getEntity());
        if (resultCode != 200) {
            throw new CognizerResponseException(result, resultCode);
        } else {
            return expectedResponseClass != null ? this.objectMapper.readValue(result, expectedResponseClass) : null;
        }
    }

    public static class CognizerUserKey {
        private final String xOrgId;
        private final String userName;
        private final String userPassword;

        public CognizerUserKey(String xOrgId, String userName, String userPassword) {
            this.xOrgId = xOrgId;
            this.userName = userName;
            this.userPassword = userPassword;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                CognizerUserKey that = (CognizerUserKey)o;
                return Objects.equals(this.xOrgId, that.xOrgId) && Objects.equals(this.userName, that.userName) && Objects.equals(this.userPassword, that.userPassword);
            } else {
                return false;
            }
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.xOrgId, this.userName, this.userPassword});
        }
    }
}
