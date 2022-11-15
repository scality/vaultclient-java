package com.scality.vaultclient.services;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.HttpResponse;
import com.scality.vaultclient.dto.AccountData;
import com.scality.vaultclient.dto.UpdateAccountAttributesRequestDTO;
import com.scality.vaultclient.dto.CreateAccountResponseDTO;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.scality.vaultclient.utils.VaultServicesTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateAccountAttributesServiceTest {

    // mock vault client
    protected static AmazonHttpClient updateAccountAttributesAmazonHttpClient;

    // dummy Aws credentials
    protected static BasicAWSCredentials basicAWSCredentials;

    // Service
    protected static AccountServicesClient updateAccountAttributesMockClient;

    // Service with aws creds
    protected static AccountServicesClient accountServicesClient;

    private static Map<String, String> customAttributes  = new HashMap<>() ;

    private static UpdateAccountAttributesRequestDTO updateAccountAttributesRequestDTO;

    @BeforeAll
    public static void init() throws Exception{

        basicAWSCredentials = new BasicAWSCredentials("accesskey", "secretkey");


        customAttributes.put("cd_tenant_id%3D%3Dccf2139b-7435-426b-b4d2-b917392d9540", "");
        customAttributes.put("cd_tenant_id%3D%3D27598f0b-3696-4d04-81f3-76f89b032d7d", "");

        initUpdateAccountAttributesMocks();

    }

    private static void initUpdateAccountAttributesMocks() {

        updateAccountAttributesRequestDTO = UpdateAccountAttributesRequestDTO.builder()
                .accountName(DEFAULT_ACCOUNT_NAME)
                .customAttributes(customAttributes)
                .build();

        updateAccountAttributesAmazonHttpClient = mock(AmazonHttpClient.class);
        updateAccountAttributesMockClient = new AccountServicesClient(updateAccountAttributesAmazonHttpClient);

        //Default Create Account mock response
        when(updateAccountAttributesAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response<CreateAccountResponseDTO> answer(InvocationOnMock invocation) {
                        Request<UpdateAccountAttributesRequestDTO> ogReq = invocation.getArgument(0);
                        UpdateAccountAttributesRequestDTO request = (UpdateAccountAttributesRequestDTO) ogReq.getOriginalRequest();
                        AccountData data = new AccountData();
                        data.setEmailAddress(DEFAULT_EMAIL_ADDR);
                        data.setName(request.getAccountName());
                        data.setArn(DEFAULT_ARN_STR + request.getAccountName() +"/\"");
                        data.setCreateDate(new Date());
                        data.setId(DEFAULT_ACCOUNT_ID);
                        data.setCanonicalId(DEFAULT_CANONICAL_ID);
                        data.setCustomAttributes(request.getCustomAttributes());
                        com.scality.vaultclient.dto.Account account = new com.scality.vaultclient.dto.Account();
                        account.setData(data);
                        CreateAccountResponseDTO response = new CreateAccountResponseDTO();
                        response.setAccount(account);
                        return new Response<>(response,null);
                    }
                });


        accountServicesClient = new AccountServicesClient(updateAccountAttributesAmazonHttpClient, basicAWSCredentials);
    }

    @Test
    public void testUpdateAccountAttributes() throws Exception {

        CreateAccountResponseDTO response = updateAccountAttributesMockClient.updateAccountAttributes(updateAccountAttributesRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    public void testUpdateAccountAttributesWithCredentials() throws Exception {

        accountServicesClient = new AccountServicesClient(updateAccountAttributesAmazonHttpClient, basicAWSCredentials);
        CreateAccountResponseDTO response = accountServicesClient.updateAccountAttributes(updateAccountAttributesRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    public void testUpdateAccountAttributesWithoutCustomAttributes() throws Exception {


        UpdateAccountAttributesRequestDTO updateAccountAttributesRequestDTO1 = UpdateAccountAttributesRequestDTO.builder()
                .accountName(DEFAULT_ACCOUNT_NAME)
                .build();

        assertThrows(SdkClientException.class, () -> {
            updateAccountAttributesMockClient.updateAccountAttributes(updateAccountAttributesRequestDTO1);
        }, "Expected VaultClientException");
    }

    @Test
    public void testUpdateAccountAttributesRequestWithNullName(){

        assertThrows(NullPointerException.class, () -> {
            UpdateAccountAttributesRequestDTO.builder()
                    .accountName(null)
                    .customAttributes(customAttributes)
                    .build();
        }, "Expected NullPointerException");
    }

    @Test
    public void testUpdateAccountAttributesError400() throws Exception {

        when(updateAccountAttributesAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response<CreateAccountResponseDTO> answer(InvocationOnMock invocation) {
                        Request<UpdateAccountAttributesRequestDTO> ogReq = invocation.getArgument(0);
                        HttpRequestBase httpRequestBase = new HttpRequestBase() {
                            @Override
                            public String getMethod() {
                                return "Not Implemented";
                            }
                        };
                        HttpResponse httpResponse = new HttpResponse(ogReq, httpRequestBase);
                        httpResponse.setStatusCode(400);
                        httpResponse.setStatusText("Bad Request");
                        return new Response<>(new CreateAccountResponseDTO(),httpResponse);
                    }
                });

        HttpResponse response = updateAccountAttributesMockClient.updateAccountAttributes(updateAccountAttributesRequestDTO).getHttpResponse();

        assertEquals(400, response.getStatusCode(), "Expected http status code: 409");

        //reinit the amazonHttpClient
        init();
    }

    /** Tests with Actual Vault **/
    @Disabled
    @Test
    @SuppressWarnings( "deprecation" )
    public void testUpdateAccountAttributesWithActualVault() {
        //"D4IT2AWSB588GO5J9T00": "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"
        AccountServicesClient amazonIdentityManagementClient = new AccountServicesClient(
                new BasicAWSCredentials("D4IT2AWSB588GO5J9T00", "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"));

        amazonIdentityManagementClient.setEndpoint("http://localhost:8600");
        String email_address = DEFAULT_EMAIL_ADDR;
        String name = DEFAULT_ACCOUNT_NAME;

        Map<String, String> customAttributes  = new HashMap<>() ;
        customAttributes.put("cd_tenant_id%3D%3Dccf2139b-7435-426b-b4d2-b917392d9540", "");
        customAttributes.put("cd_tenant_id%3D%3D27598f0b-3696-4d04-81f3-76f89b032d7d", "");
        UpdateAccountAttributesRequestDTO updateAccountAttributesRequestDTOs = UpdateAccountAttributesRequestDTO.builder()
                .accountName(name)
                .customAttributes(customAttributes)
                .build();
        CreateAccountResponseDTO response = amazonIdentityManagementClient.updateAccountAttributes(updateAccountAttributesRequestDTOs).getAwsResponse();
        assertEquals(email_address, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(name, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

}
