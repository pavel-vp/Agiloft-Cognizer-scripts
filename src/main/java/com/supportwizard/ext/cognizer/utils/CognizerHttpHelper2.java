//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer.utils;

import com.supportwizard.ext.cognizer.model.CognizerUser;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestRequest;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerIngestStatusResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerOrderCreateResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerQNARequest;
import com.supportwizard.ext.cognizer.model.api.CognizerQNAResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantAssignmentResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerTenantCreateResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerTokenResponse;
import com.supportwizard.ext.cognizer.model.api.CognizerUserAssignmentListResponse;

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

    void reserveUserSubscription(String var1, String var2, String var3, String var4, Long var5) throws Exception;

    CognizerUserAssignmentListResponse userAssignmentList(String var1, String var2) throws Exception;
}
