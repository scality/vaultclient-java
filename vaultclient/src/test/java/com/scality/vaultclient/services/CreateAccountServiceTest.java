package com.scality.vaultclient.services;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.HttpResponse;
import com.scality.vaultclient.dto.AccountData;
import com.scality.vaultclient.dto.CreateAccountRequestDTO;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateAccountServiceTest {

    // mock vault client
    protected static AmazonHttpClient createAccountAmazonHttpClient;

    // dummy Aws credentials
    protected static BasicAWSCredentials basicAWSCredentials;

    // Service
    protected static AccountServicesClient createAccountMockClient;

    // Service with aws creds
    protected static AccountServicesClient accountServicesClient;

    private static Map<String, String> customAttributes  = new HashMap<>() ;

    private static CreateAccountRequestDTO createAccountRequestDTO;

    @BeforeAll
    public static void init() throws Exception{

        basicAWSCredentials = new BasicAWSCredentials("accesskey", "secretkey");


        customAttributes.put("cd_tenant_id%3D%3Dccf2139b-7435-426b-b4d2-b917392d9540", "");
        customAttributes.put("cd_tenant_id%3D%3D27598f0b-3696-4d04-81f3-76f89b032d7d", "");

        initCreateAccountMocks();

    }

    private static void initCreateAccountMocks() {

        createAccountRequestDTO = CreateAccountRequestDTO.builder()
                .emailAddress(DEFAULT_EMAIL_ADDR)
                .name(DEFAULT_ACCOUNT_NAME)
                .customAttributes(customAttributes)
                .build();

        createAccountAmazonHttpClient = mock(AmazonHttpClient.class);
        createAccountMockClient = new AccountServicesClient(createAccountAmazonHttpClient);

        //Default Create Account mock response
        when(createAccountAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response<CreateAccountResponseDTO> answer(InvocationOnMock invocation) {
                        Request<CreateAccountRequestDTO> ogReq = invocation.getArgument(0);
                        CreateAccountRequestDTO request = (CreateAccountRequestDTO) ogReq.getOriginalRequest();
                        AccountData data = new AccountData();
                        data.setEmailAddress(request.getEmailAddress());
                        data.setName(request.getName());
                        data.setArn(DEFAULT_ARN_STR + request.getName() +"/\"");
                        data.setCreateDate(new Date());
                        if(request.getExternalAccountId() == null) {
                            data.setId(DEFAULT_ACCOUNT_ID);
                        } else {
                            data.setId(request.getExternalAccountId());
                        }
                        data.setCanonicalId(DEFAULT_CANONICAL_ID);
                        data.setQuotaMax(request.getQuotaMax());
                        data.setCustomAttributes(request.getCustomAttributes());
                        com.scality.vaultclient.dto.Account account = new com.scality.vaultclient.dto.Account();
                        account.setData(data);
                        CreateAccountResponseDTO response = new CreateAccountResponseDTO();
                        response.setAccount(account);
                        return new Response<>(response,null);
                    }
                });


        accountServicesClient = new AccountServicesClient(createAccountAmazonHttpClient, basicAWSCredentials);
    }

    @Test
    void testCreateAccount() throws Exception {

        CreateAccountResponseDTO response = createAccountMockClient.createAccount(createAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    void testCreateAccountWithCredentials() throws Exception {

        accountServicesClient = new AccountServicesClient(createAccountAmazonHttpClient, basicAWSCredentials);
        CreateAccountResponseDTO response = accountServicesClient.createAccount(createAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    void testCreateAccountWithoutCustomAttributes() throws Exception {


        CreateAccountRequestDTO createAccountRequestDTO1 = CreateAccountRequestDTO.builder()
                .emailAddress(DEFAULT_EMAIL_ADDR)
                .name(DEFAULT_ACCOUNT_NAME)
                .quotaMax(DEFAULT_QUOTA_MAX)
                .build();

        CreateAccountResponseDTO response = accountServicesClient.createAccount(createAccountRequestDTO1).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_IS_NULL);
    }

    @Test
    void testCreateAccountWithQuota() throws Exception {

        CreateAccountRequestDTO createAccountRequestDTO1 = CreateAccountRequestDTO.builder()
                .emailAddress(DEFAULT_EMAIL_ADDR)
                .name(DEFAULT_ACCOUNT_NAME)
                .customAttributes(customAttributes)
                .quotaMax(DEFAULT_QUOTA_MAX)
                .build();

        CreateAccountResponseDTO response = createAccountMockClient.createAccount(createAccountRequestDTO1).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
        assertEquals(DEFAULT_QUOTA_MAX, response.getAccount().getData().getQuotaMax(), "Invalid QuotaMax" );
    }

    @Test
    void testCreateAccountWithExtId() throws Exception {

        CreateAccountRequestDTO createAccountRequestDTO2 = CreateAccountRequestDTO.builder()
                .emailAddress(DEFAULT_EMAIL_ADDR)
                .name(DEFAULT_ACCOUNT_NAME)
                .customAttributes(customAttributes)
                .externalAccountId("249349283982")
                .build();

        CreateAccountResponseDTO response = createAccountMockClient.createAccount(createAccountRequestDTO2).getAwsResponse();
        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertEquals("249349283982", response.getAccount().getData().getId(), "Invalid Id");
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    void testCreateAccountRequestWithNullEmail(){

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO.builder()
                    .emailAddress(null)
                    .name(DEFAULT_ACCOUNT_NAME)
                    .customAttributes(customAttributes)
                    .build();
        }, "Expected NullPointerException");
    }

    @Test
    void testCreateAccountRequestWithNullName(){

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO.builder()
                    .emailAddress(DEFAULT_EMAIL_ADDR)
                    .name(null)
                    .customAttributes(customAttributes)
                    .build();
        }, "Expected NullPointerException");
    }

    @Test
    void createAccountErrorExistingAccount() throws Exception {

        final String ENTITY_EXISTS_ERR = "The request was rejected because it attempted to create a resource that already exists.";

        when(createAccountAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response<CreateAccountResponseDTO> answer(InvocationOnMock invocation) {
                        VaultClientException e = new VaultClientException("EntityAlreadyExists");
                        e.setErrorCode("EntityAlreadyExists");
                        e.setErrorMessage(ENTITY_EXISTS_ERR);
                        e.setStatusCode(409);
                        e.setServiceName("Vault");
                        throw e;
                    }
                });

        VaultClientException e = assertThrows(VaultClientException.class, () -> {
            createAccountMockClient.createAccount(createAccountRequestDTO);
        }, "Expected VaultClientException");
        assertEquals(409, e.getStatusCode(), "Expected http status code: 409");
        assertEquals("EntityAlreadyExists", e.getErrorCode(), "Expected error code: EntityAlreadyExists");
        assertEquals(ENTITY_EXISTS_ERR, e.getErrorMessage(), "Invalid error message");

        //reinit the amazonHttpClient
        init();
    }

    @Test
    void testCreateAccountError400() throws Exception {

        when(createAccountAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response<CreateAccountResponseDTO> answer(InvocationOnMock invocation) {
                        Request<CreateAccountRequestDTO> ogReq = invocation.getArgument(0);
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

        HttpResponse response = createAccountMockClient.createAccount(createAccountRequestDTO).getHttpResponse();

        assertEquals(400, response.getStatusCode(), "Expected http status code: 409");

        //reinit the amazonHttpClient
        init();
    }

    /** Tests with Actual Vault **/
    @Disabled
    @Test
    @SuppressWarnings( "deprecation" )
    void testCreateAccountWithActualVault() {
        //"D4IT2AWSB588GO5J9T00": "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"
        AccountServicesClient amazonIdentityManagementClient = new AccountServicesClient(
                new BasicAWSCredentials("D4IT2AWSB588GO5J9T00", "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"));

        amazonIdentityManagementClient.setEndpoint("http://localhost:8600");
        String email_address = DEFAULT_EMAIL_ADDR;
        String name = DEFAULT_ACCOUNT_NAME;

        Map<String, String> customAttributes  = new HashMap<>() ;
        customAttributes.put("cd_tenant_id%3D%3Dccf2139b-7435-426b-b4d2-b917392d9540", "");
        customAttributes.put("cd_tenant_id%3D%3D27598f0b-3696-4d04-81f3-76f89b032d7d", "");
        CreateAccountRequestDTO createAccountRequestDTOs = CreateAccountRequestDTO.builder()
                .emailAddress(email_address)
                .name(name)
                .customAttributes(customAttributes)
                .build();
        CreateAccountResponseDTO response = amazonIdentityManagementClient.createAccount(createAccountRequestDTOs).getAwsResponse();
        assertEquals(email_address, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(name, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

}
