package com.scality.vaultclient.services;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.scality.vaultclient.dto.AccountData;
import com.scality.vaultclient.dto.GetAccountRequestDTO;
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

public class GetAccountServiceTest {

    // mock vault client
    protected static AmazonHttpClient getAccountAmazonHttpClient;

    // dummy Aws credentials
    protected static BasicAWSCredentials basicAWSCredentials;

    // Service
    protected static AccountServicesClient getAccountMockClient;

    // Service with aws creds
    protected static AccountServicesClient accountServicesClient;

    private static Map<String, String> customAttributes  = new HashMap<>() ;

    @BeforeAll
    public static void init() throws Exception{

        basicAWSCredentials = new BasicAWSCredentials("accesskey", "secretkey");

        customAttributes.put("cd_tenant_id%3D%3Dccf2139b-7435-426b-b4d2-b917392d9540", "");
        customAttributes.put("cd_tenant_id%3D%3D27598f0b-3696-4d04-81f3-76f89b032d7d", "");

        initGetAccountMocks();

    }

    private static void initGetAccountMocks() {

        getAccountAmazonHttpClient = mock(AmazonHttpClient.class);
        getAccountMockClient = new AccountServicesClient(getAccountAmazonHttpClient);

        //Default Get Account mock response
        when(getAccountAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response<AccountData> answer(InvocationOnMock invocation) {
                        Request<GetAccountRequestDTO> ogReq = invocation.getArgument(0);
                        GetAccountRequestDTO request = (GetAccountRequestDTO) ogReq.getOriginalRequest();
                        final String accountId = request.getAccountId() !=null ? request.getAccountId() : DEFAULT_ACCOUNT_ID;
                        final String accountName = request.getAccountName() !=null ? request.getAccountName() : DEFAULT_ACCOUNT_NAME;
                        final String accountArn = request.getAccountArn() !=null ? request.getAccountArn() : DEFAULT_ARN_STR + accountName +"/\"";
                        final String emailAddress = request.getEmailAddress() !=null ? request.getEmailAddress() : DEFAULT_EMAIL_ADDR;
                        final String canonicalId = request.getCanonicalId() !=null ? request.getCanonicalId() : DEFAULT_CANONICAL_ID;

                        AccountData data = new AccountData();
                        data.setEmailAddress(emailAddress);
                        data.setName(accountName);
                        data.setArn(accountArn);
                        data.setCreateDate(new Date());
                        data.setId(accountId);
                        data.setCanonicalId(canonicalId);
                        data.setCustomAttributes(customAttributes);


                        return new Response<AccountData>(data,null);
                    }
                });
    }

    /** Get AccountData Test cases **/
    @Test
    public void testGetAccountWithAccountId() throws Exception {
        GetAccountRequestDTO getAccountRequestDTO = GetAccountRequestDTO.builder()
                .accountId(DEFAULT_ACCOUNT_ID)
                .build();

        AccountData response = getAccountMockClient.getAccount(getAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getName(), ERR_NAME_INVALID);
        assertNotNull(response.getArn(), ERR_ARN_NULL);
        assertNotNull(response.getCreateDate(), ERR_CREATE_DATE_NULL);
        assertEquals(DEFAULT_ACCOUNT_ID, response.getId(), ERR_ID_NULL);
        assertEquals(DEFAULT_CANONICAL_ID, response.getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    public void testGetAccountWithEmail() throws Exception {
        GetAccountRequestDTO getAccountRequestDTO = GetAccountRequestDTO.builder()
                .emailAddress(DEFAULT_EMAIL_ADDR)
                .build();

        AccountData response = getAccountMockClient.getAccount(getAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getName(), ERR_NAME_INVALID);
        assertNotNull(response.getArn(), ERR_ARN_NULL);
        assertNotNull(response.getCreateDate(), ERR_CREATE_DATE_NULL);
        assertEquals(DEFAULT_ACCOUNT_ID, response.getId(), ERR_ID_NULL);
        assertEquals(DEFAULT_CANONICAL_ID, response.getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    public void testGetAccountWithAccountArn() throws Exception {
        final String accountArn = DEFAULT_ARN_STR + DEFAULT_ACCOUNT_NAME +"/\"";

        GetAccountRequestDTO getAccountRequestDTO = GetAccountRequestDTO.builder()
                .accountArn(accountArn)
                .build();

        AccountData response = getAccountMockClient.getAccount(getAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getName(), ERR_NAME_INVALID);
        assertNotNull(response.getArn(), ERR_ARN_NULL);
        assertNotNull(response.getCreateDate(), ERR_CREATE_DATE_NULL);
        assertEquals(DEFAULT_ACCOUNT_ID, response.getId(), ERR_ID_NULL);
        assertEquals(DEFAULT_CANONICAL_ID, response.getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    public void testGetAccountWithCanonicalID() throws Exception {
        GetAccountRequestDTO getAccountRequestDTO = GetAccountRequestDTO.builder()
                .canonicalId(DEFAULT_CANONICAL_ID)
                .build();

        AccountData response = getAccountMockClient.getAccount(getAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getName(), ERR_NAME_INVALID);
        assertNotNull(response.getArn(), ERR_ARN_NULL);
        assertNotNull(response.getCreateDate(), ERR_CREATE_DATE_NULL);
        assertEquals(DEFAULT_ACCOUNT_ID, response.getId(), ERR_ID_NULL);
        assertEquals(DEFAULT_CANONICAL_ID, response.getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    public void testGetAccountWithAccountName() throws Exception {
        GetAccountRequestDTO getAccountRequestDTO = GetAccountRequestDTO.builder()
                .accountName(DEFAULT_ACCOUNT_NAME)
                .build();

        AccountData response = getAccountMockClient.getAccount(getAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getName(), ERR_NAME_INVALID);
        assertNotNull(response.getArn(), ERR_ARN_NULL);
        assertNotNull(response.getCreateDate(), ERR_CREATE_DATE_NULL);
        assertEquals(DEFAULT_ACCOUNT_ID, response.getId(), ERR_ID_NULL);
        assertEquals(DEFAULT_CANONICAL_ID, response.getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

    @Test
    public void testGetAccountWithCredentials() throws Exception {
        GetAccountRequestDTO getAccountRequestDTO = GetAccountRequestDTO.builder()
                .accountName(DEFAULT_ACCOUNT_NAME)
                .build();

        accountServicesClient = new AccountServicesClient(getAccountAmazonHttpClient, basicAWSCredentials);
        AccountData response = accountServicesClient.getAccount(getAccountRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getName(), ERR_NAME_INVALID);
        assertNotNull(response.getArn(), ERR_ARN_NULL);
        assertNotNull(response.getCreateDate(), ERR_CREATE_DATE_NULL);
        assertEquals(DEFAULT_ACCOUNT_ID, response.getId(), ERR_ID_NULL);
        assertEquals(DEFAULT_CANONICAL_ID, response.getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
        init();
    }

    @Test
    public void testGetAccountWithInvalidRequest(){
        GetAccountRequestDTO getAccountRequestDTO = GetAccountRequestDTO.builder().build();

        assertThrows(SdkClientException.class, () -> {
            getAccountMockClient.getAccount(getAccountRequestDTO).getAwsResponse();
        }, "Expected SdkClientException");
    }

    @Test
    public void testGetAccountWithInvalidRequest2(){
        GetAccountRequestDTO getAccountRequestDTO = GetAccountRequestDTO.builder()
                                                    .accountName(DEFAULT_ACCOUNT_NAME)
                                                    .accountArn(DEFAULT_ARN_STR)
                                                    .build();

        assertThrows(SdkClientException.class, () -> {
            getAccountMockClient.getAccount(getAccountRequestDTO).getAwsResponse();
        }, "Expected SdkClientException");
    }

    /** Tests with Actual Vault **/
    @Disabled
    @Test
    @SuppressWarnings( "deprecation" )
    public void testGetAccountWithActualVault() {
        //"D4IT2AWSB588GO5J9T00": "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"
        AccountServicesClient amazonIdentityManagementClient = new AccountServicesClient(
                new BasicAWSCredentials("D4IT2AWSB588GO5J9T00", "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"));

        amazonIdentityManagementClient.setEndpoint("http://localhost:8600");

        GetAccountRequestDTO getAccountRequestDTO = GetAccountRequestDTO.builder()
                .accountId("875562344436")
                .build();
        AccountData response = amazonIdentityManagementClient.getAccount(getAccountRequestDTO).getAwsResponse();

        assertEquals("875562344436", response.getId(), ERR_ID_NULL);
        assertNotNull(response.getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertNotNull(response.getName(), ERR_NAME_INVALID);
        assertNotNull(response.getArn(), ERR_ARN_NULL);
        assertNotNull(response.getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getId(), ERR_ID_NULL);
        assertNotNull(response.getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
    }

}
