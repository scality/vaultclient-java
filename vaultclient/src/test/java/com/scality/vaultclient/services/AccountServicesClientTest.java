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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServicesClientTest {

    // mock vault client
    protected static AmazonHttpClient amazonHttpClient;

    // dummy Aws credentials
    protected static BasicAWSCredentials basicAWSCredentials;

    // Service
    protected static AccountServicesClient accountServicesClient;

    // Service with aws creds
    protected static AccountServicesClient accountServicesClient2;

    private static final String DEFAULT_ACCOUNT_ID = "001583654825";

    private static final String DEFAULT_ARN_STR = "\"arn:aws:iam::001583654825:/";

    private static final String DEFAULT_CANONICAL_ID = "31e38bcfda3ab1887587669ee25a348cc89e6e2e87dc38088289b1b3c5329b30";

    private static final String DEFAULT_ACCOUNT_NAME = "Account5425";

    private static final String DEFAULT_EMAIL_ADDR = "xyz@scality.com";

    private static final String ERR_EMAIL_ADDR_INVALID = "Invalid EmailAddress";
    private static final String ERR_NAME_INVALID = "Invalid Name";
    private static final String ERR_ARN_NULL = "Arn cannot be null";
    private static final String ERR_CREATE_DATE_NULL = "CreateDate cannot be null";
    private static final String ERR_ID_NULL = "Id cannot be null";
    private static final String ERR_CANONICAL_ID_NULL = "CanonicalId cannot be null";


    private static final CreateAccountRequestDTO createAccountRequestDTO = CreateAccountRequestDTO.builder()
            .emailAddress(DEFAULT_EMAIL_ADDR)
            .name(DEFAULT_ACCOUNT_NAME)
            .build();

    @BeforeAll
    public static void init() throws Exception{

        amazonHttpClient = mock(AmazonHttpClient.class);
        accountServicesClient = new AccountServicesClient(amazonHttpClient);

        //Default Create Account mock response
        when(amazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
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
                        com.scality.vaultclient.dto.Account account = new com.scality.vaultclient.dto.Account();
                        account.setData(data);
                        CreateAccountResponseDTO response = new CreateAccountResponseDTO();
                        response.setAccount(account);
                        return new Response<>(response,null);
                    }
                });

        basicAWSCredentials = new BasicAWSCredentials("accesskey", "secretkey");
        accountServicesClient2 = new AccountServicesClient(amazonHttpClient, basicAWSCredentials);
    }

    @Test
    public void testCreateAccount() throws Exception {

        CreateAccountResponseDTO response = accountServicesClient.createAccount(createAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
    }

    @Test
    public void testCreateAccountWithCredentials() throws Exception {

        CreateAccountResponseDTO response = accountServicesClient2.createAccount(createAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
    }

    @Test
    public void testCreateAccountWithQuota() throws Exception {

        CreateAccountRequestDTO createAccountRequestDTO1 = CreateAccountRequestDTO.builder()
                .emailAddress(DEFAULT_EMAIL_ADDR)
                .name(DEFAULT_ACCOUNT_NAME)
                .quotaMax(10)
                .build();

        CreateAccountResponseDTO response = accountServicesClient.createAccount(createAccountRequestDTO1).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertEquals(10, response.getAccount().getData().getQuotaMax(), "Invalid QuotaMax" );
    }

    @Test
    public void testCreateAccountWithExtId() throws Exception {

        CreateAccountRequestDTO createAccountRequestDTO2 = CreateAccountRequestDTO.builder()
                .emailAddress(DEFAULT_EMAIL_ADDR)
                .name(DEFAULT_ACCOUNT_NAME)
                .externalAccountId("249349283982")
                .build();

        CreateAccountResponseDTO response = accountServicesClient.createAccount(createAccountRequestDTO2).getAwsResponse();
        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertEquals("249349283982", response.getAccount().getData().getId(), "Invalid Id");
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
    }

    @Test
    public void testCreateAccountRequestWithNullEmail(){

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO.builder()
                    .emailAddress(null)
                    .name(DEFAULT_ACCOUNT_NAME)
                    .build();
        }, "Expected NullPointerException");
    }

    @Test
    public void testCreateAccountRequestWithNullName(){

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO.builder()
                    .emailAddress(DEFAULT_EMAIL_ADDR)
                    .name(null)
                    .build();
        }, "Expected NullPointerException");
    }

    @Test
    public void createAccountErrorExistingAccount() throws Exception {

        final String ENTITY_EXISTS_ERR = "The request was rejected because it attempted to create a resource that already exists.";

        when(amazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
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
            accountServicesClient.createAccount(createAccountRequestDTO);
        }, "Expected VaultClientException");
        assertEquals(409, e.getStatusCode(), "Expected http status code: 409");
        assertEquals("EntityAlreadyExists", e.getErrorCode(), "Expected error code: EntityAlreadyExists");
        assertEquals(ENTITY_EXISTS_ERR, e.getErrorMessage(), "Invalid error message");

        //reinit the amazonHttpClient
        init();
    }

    @Test
    public void testCreateAccountError400() throws Exception {

        when(amazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
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

        HttpResponse response = accountServicesClient.createAccount(createAccountRequestDTO).getHttpResponse();

        assertEquals(400, response.getStatusCode(), "Expected http status code: 409");

        //reinit the amazonHttpClient
        init();
    }

    @Disabled
    @Test
    @SuppressWarnings( "deprecation" )
    public void testCreateAccountWithActualVault() {
        //"D4IT2AWSB588GO5J9T00": "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"
        AccountServicesClient amazonIdentityManagementClient = new AccountServicesClient(
                new BasicAWSCredentials("accesskey", "secretkey"));

        amazonIdentityManagementClient.setEndpoint("http://localhost:8600");
        String email_address = DEFAULT_EMAIL_ADDR;
        String name = DEFAULT_ACCOUNT_NAME;
        CreateAccountRequestDTO createAccountRequestDTOs = CreateAccountRequestDTO.builder()
                .emailAddress(email_address)
                .name(name)
                //                    .externalAccountId("249349283982")
                //                    .quotaMax(10)
                .build();
        CreateAccountResponseDTO response = amazonIdentityManagementClient.createAccount(createAccountRequestDTOs).getAwsResponse();
        assertEquals(email_address, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(name, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
    }

}
