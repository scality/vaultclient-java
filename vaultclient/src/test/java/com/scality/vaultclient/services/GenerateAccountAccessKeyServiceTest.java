package com.scality.vaultclient.services;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.HttpResponse;
import com.scality.vaultclient.dto.AccountSecretKeyData;
import com.scality.vaultclient.dto.CreateAccountRequestDTO;
import com.scality.vaultclient.dto.GenerateAccountAccessKeyRequest;
import com.scality.vaultclient.dto.GenerateAccountAccessKeyResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;

import static com.scality.vaultclient.utils.VaultServicesTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GenerateAccountAccessKeyServiceTest {

    // mock vault client
    protected static AmazonHttpClient generateAccountAccessKeyAmazonHttpClient;

    // dummy Aws credentials
    protected static BasicAWSCredentials basicAWSCredentials;

    // Service
    protected static AccountServicesClient generateAccountAccessKeyMockClient;

    // Service with aws creds
    protected static AccountServicesClient accountServicesClient;

    private static GenerateAccountAccessKeyRequest generateAccountAccessKeyRequest;

    @BeforeAll
    public static void init(){

        basicAWSCredentials = new BasicAWSCredentials(TEST_ACCESS_KEY, TEST_SECRET_KEY);

        initGenerateAccountAccessKeyMocks();

    }

    private static void initGenerateAccountAccessKeyMocks() {

        generateAccountAccessKeyRequest = GenerateAccountAccessKeyRequest.builder()
                                            .accountName(DEFAULT_ACCOUNT_NAME)
                                            .build();

        generateAccountAccessKeyAmazonHttpClient = mock(AmazonHttpClient.class);
        generateAccountAccessKeyMockClient = new AccountServicesClient(generateAccountAccessKeyAmazonHttpClient);

        //Default Generate Account Access Key mock response
        when(generateAccountAccessKeyAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer((Answer<Response>) invocation -> {
                    Request<CreateAccountRequestDTO> ogReq = invocation.getArgument(0);
                    GenerateAccountAccessKeyRequest request = (GenerateAccountAccessKeyRequest) ogReq.getOriginalRequest();

                    final AccountSecretKeyData accountSecretKeyData = new AccountSecretKeyData().builder()
                            .id( (request.getExternalAccessKey() == null) ? TEST_ACCESS_KEY :request.getExternalAccessKey() )
                            .value( (request.getExternalSecretKey() == null) ? TEST_SECRET_KEY :request.getExternalSecretKey())
                            .userId(DEFAULT_ACCOUNT_ID)
                            .createDate(new Date())
                            .lastUsedService(NA_STR)
                            .lastUsedRegion(NA_STR)
                            .lastUsedDate(new Date())
                            .status(ACTIVE_STR)
                            .build();

                    if(request.getDurationSeconds() > 0){
                        Date afterDate = new Date();
                        afterDate.setTime(afterDate.getTime() + (request.getDurationSeconds() * 1000L));
                        accountSecretKeyData.setNotAfter(afterDate);
                    }
                    final GenerateAccountAccessKeyResponse response = new GenerateAccountAccessKeyResponse();
                    response.setData(accountSecretKeyData);
                    return new Response<>(response,null);
                });


        accountServicesClient = new AccountServicesClient(generateAccountAccessKeyAmazonHttpClient, basicAWSCredentials);
    }

    @Test
    void testGenerateAccountAccessKey() {

        GenerateAccountAccessKeyResponse response = generateAccountAccessKeyMockClient.generateAccountAccessKey(generateAccountAccessKeyRequest).getAwsResponse();

        assertNotNull(response.getData());
        AccountSecretKeyData secretKeyData = response.getData();
        assertEquals(TEST_ACCESS_KEY, secretKeyData.getId());
        assertEquals(TEST_SECRET_KEY, secretKeyData.getValue());
        assertEquals(ACTIVE_STR, secretKeyData.getStatus());
        assertNotNull(secretKeyData.getCreateDate());
        assertNotNull(secretKeyData.getLastUsedDate());
        assertNotNull(secretKeyData.getLastUsedService());
        assertNotNull(secretKeyData.getLastUsedRegion());
    }

    @Test
    void testGenerateAccountAccessKeyWithCredentials() {

        accountServicesClient = new AccountServicesClient(generateAccountAccessKeyAmazonHttpClient, basicAWSCredentials);
        GenerateAccountAccessKeyResponse response = accountServicesClient.generateAccountAccessKey(generateAccountAccessKeyRequest).getAwsResponse();

        assertNotNull(response.getData());
        AccountSecretKeyData secretKeyData = response.getData();
        assertEquals(TEST_ACCESS_KEY, secretKeyData.getId());
        assertEquals(TEST_SECRET_KEY, secretKeyData.getValue());
        assertEquals(ACTIVE_STR, secretKeyData.getStatus());
        assertNotNull(secretKeyData.getCreateDate());
        assertNotNull(secretKeyData.getLastUsedDate());
        assertNotNull(secretKeyData.getLastUsedService());
        assertNotNull(secretKeyData.getLastUsedRegion());
    }

    @Test
    void testGenerateAccountAccessKeyWithDurationSeconds() {

        GenerateAccountAccessKeyRequest GenerateAccountAccessKeyRequest1 = GenerateAccountAccessKeyRequest.builder()
                                                                            .accountName(DEFAULT_ACCOUNT_NAME)
                                                                            .durationSeconds(TEST_ACCESS_KEY_DURATION_SECONDS)
                                                                            .build();

        GenerateAccountAccessKeyResponse response = generateAccountAccessKeyMockClient.generateAccountAccessKey(GenerateAccountAccessKeyRequest1).getAwsResponse();

        assertNotNull(response.getData());
        AccountSecretKeyData secretKeyData = response.getData();
        assertNotNull(secretKeyData.getNotAfter());
        assertEquals(TEST_ACCESS_KEY, secretKeyData.getId());
        assertEquals(TEST_SECRET_KEY, secretKeyData.getValue());
        assertEquals(ACTIVE_STR, secretKeyData.getStatus());
        assertNotNull(secretKeyData.getCreateDate());
        assertNotNull(secretKeyData.getLastUsedDate());
        assertNotNull(secretKeyData.getLastUsedService());
        assertNotNull(secretKeyData.getLastUsedRegion());
    }

    @Test
    void testGenerateAccountAccessKeyWithExtKeys() throws Exception {
        GenerateAccountAccessKeyRequest GenerateAccountAccessKeyRequest2 = GenerateAccountAccessKeyRequest.builder()
                                                                            .accountName(DEFAULT_ACCOUNT_NAME)
                                                                            .externalAccessKey(TEST_ACCESS_KEY1)
                                                                            .externalSecretKey(TEST_SECRET_KEY1)
                                                                            .build();

        GenerateAccountAccessKeyResponse response = generateAccountAccessKeyMockClient.generateAccountAccessKey(GenerateAccountAccessKeyRequest2).getAwsResponse();
        assertNotNull(response.getData());
        AccountSecretKeyData secretKeyData = response.getData();
        assertEquals(TEST_ACCESS_KEY1, secretKeyData.getId());
        assertEquals(TEST_SECRET_KEY1, secretKeyData.getValue());
        assertEquals(ACTIVE_STR, secretKeyData.getStatus());
        assertNotNull(secretKeyData.getCreateDate());
        assertNotNull(secretKeyData.getLastUsedDate());
        assertNotNull(secretKeyData.getLastUsedService());
        assertNotNull(secretKeyData.getLastUsedRegion());
    }

    @Test
    void testGenerateAccountAccessKeyRequestWithNullAccountName(){

        assertThrows(NullPointerException.class, () -> {
            GenerateAccountAccessKeyRequest.builder()
                    .accountName(null)
                    .build();
        }, "Expected NullPointerException");
    }

    @Test
    void testListAccountsWithInvalidDuration(){
        GenerateAccountAccessKeyRequest GenerateAccountAccessKeyRequest1 = GenerateAccountAccessKeyRequest.builder()
                .accountName(DEFAULT_ACCOUNT_NAME)
                .durationSeconds(TEST_ACCESS_KEY_DURATION_SECONDS_ERR)
                .build();
        assertThrows(SdkClientException.class, () -> {
            generateAccountAccessKeyMockClient.generateAccountAccessKey(GenerateAccountAccessKeyRequest1).getAwsResponse();
        }, "Expected SdkClientException");
    }

    @Test
    void testGenerateAccountAccessKeyError400() throws Exception {

        when(generateAccountAccessKeyAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response<GenerateAccountAccessKeyResponse> answer(InvocationOnMock invocation) {
                        Request<GenerateAccountAccessKeyRequest> ogReq = invocation.getArgument(0);
                        HttpRequestBase httpRequestBase = new HttpRequestBase() {
                            @Override
                            public String getMethod() {
                                return "Not Implemented";
                            }
                        };
                        HttpResponse httpResponse = new HttpResponse(ogReq, httpRequestBase);
                        httpResponse.setStatusCode(400);
                        httpResponse.setStatusText("Bad Request");
                        return new Response<>(new GenerateAccountAccessKeyResponse(),httpResponse);
                    }
                });

        HttpResponse response = generateAccountAccessKeyMockClient.generateAccountAccessKey(generateAccountAccessKeyRequest).getHttpResponse();

        assertEquals(400, response.getStatusCode(), "Expected http status code: 409");

        //reinit the amazonHttpClient
        init();
    }

    /** Tests with Actual Vault **/
    @Disabled
    @Test
    @SuppressWarnings( "deprecation" )
    void testGenerateAccountAccessKeyWithActualVault() {
        //"D4IT2AWSB588GO5J9T00": "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"
        AccountServicesClient amazonIdentityManagementClient = new AccountServicesClient(
                new BasicAWSCredentials("D4IT2AWSB588GO5J9T00", "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"));

        amazonIdentityManagementClient.setEndpoint("http://localhost:8600");
        GenerateAccountAccessKeyRequest GenerateAccountAccessKeyRequest1 = GenerateAccountAccessKeyRequest.builder()
                                                                            .accountName("boozy-white-sparrow")
//                                                                            .durationSeconds(TEST_ACCESS_KEY_DURATION_SECONDS)
                                                                            .build();
        GenerateAccountAccessKeyResponse response = amazonIdentityManagementClient.generateAccountAccessKey(GenerateAccountAccessKeyRequest1).getAwsResponse();

        assertNotNull(response.getData());
        AccountSecretKeyData secretKeyData = response.getData();
//        assertNotNull(secretKeyData.getNotAfter());
        assertNotNull(secretKeyData.getId());
        assertNotNull(secretKeyData.getValue());
        assertEquals(ACTIVE_STR, secretKeyData.getStatus());
        assertNotNull(secretKeyData.getCreateDate());
        assertNotNull(secretKeyData.getLastUsedDate());
        assertNotNull(secretKeyData.getLastUsedService());
        assertNotNull(secretKeyData.getLastUsedRegion());
    }

}
