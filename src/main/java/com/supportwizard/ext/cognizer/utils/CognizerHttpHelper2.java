//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.utils;

import com.supportwizard.ext.cognizer.model.CognizerUser;
import com.supportwizard.ext.cognizer.model.api.*;

public interface CognizerHttpHelper2 {
    void register(CognizerUser var1) throws Exception;

    CognizerTokenResponse getToken(String var1, String var2) throws Exception;

    CognizerIngestResponse ingestData(CognizerIngestRequest var1, String var2, String var3) throws Exception;

    void sendACL(CognizerIngestRequest var1, String var2, String var3) throws Exception;

    CognizerIngestStatusResponse getStatus(String var1, String var2, String var3, String var4) throws Exception;

    CognizerQNAResponse getAnswer(CognizerQNARequest var1, String var2, String var3) throws Exception;

    CognizerOrderCreateResponse createOrder(String var1, String var2, String var3, String var4, String var5, Long var6) throws Exception;

    CognizerTenantCreateResponse createTenant(String var1, String var2, String var3, String var4, String var5, String var6, String var7) throws Exception;

    CognizerTenantAssignmentResponse assignTenant(String var1, String var2, String var3, String var4, boolean var5) throws Exception;

    void tenantUpgrade(String var1, String var2, String var3) throws Exception;

    void tenantDeactivation(String var1, String var2, String var3) throws Exception;
    void tenantDeactivationV2(String var1, String var2, String var3) throws Exception;

    void reserveUserSubscription(String var1, String var2, String var3, String var4, Long var5) throws Exception;

    CognizerUserAssignmentListResponse userAssignmentList(String var1, String var2) throws Exception;

    CognizerTenantCreateResponseV2 createTenantV2(String adminUsername, String adminPassword, String tenantType, String privacyMode, String tenantName, String tenantAdminEmail, String tenantDomain, String[] productSkuList, int expiryMonths) throws Exception;

    CognizerTenantUpdateSKUResponseV2 updateTenantSKUV2(String adminUsername, String adminPassword, String tenantId, String[] productSkuList) throws Exception;

    CognizerCreateUserOrderResponseV2 createUserOrderV2(String adminUsername, String adminPassword, String tenantId, Long licNumber) throws Exception;

    CognizerTenantSKUResponseV2 getTenantSKUV2(String adminUsername, String adminPassword, String tenantId) throws Exception;
}
