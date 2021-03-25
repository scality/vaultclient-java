package com.scality.vaultclient.services;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.HttpResponse;
import com.scality.vaultclient.dto.AccountData;
import com.scality.vaultclient.dto.CreateAccountRequestDTO;
import com.scality.vaultclient.dto.CreateAccountResponseDTO;
import com.scality.vaultclient.dto.ListAccountsRequestDTO;
import com.scality.vaultclient.dto.ListAccountsResponseDTO;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServicesClientTest {

    // mock vault client
    protected static AmazonHttpClient createAccountAmazonHttpClient;
    protected static AmazonHttpClient listAccountAmazonHttpClient;

    // dummy Aws credentials
    protected static BasicAWSCredentials basicAWSCredentials;

    // Service
    protected static AccountServicesClient createAccountMockClient;
    protected static AccountServicesClient listAccountsMockClient;

    // Service with aws creds
    protected static AccountServicesClient accountServicesClient;

    private static final String DEFAULT_ACCOUNT_ID = "001583654825";

    private static final String DEFAULT_ARN_STR = "\"arn:aws:iam::001583654825:/";

    private static final String DEFAULT_CANONICAL_ID = "31e38bcfda3ab1887587669ee25a348cc89e6e2e87dc38088289b1b3c5329b30";

    private static final String DEFAULT_ACCOUNT_NAME = "Account5425";

    private static final String DEFAULT_EMAIL_ADDR = "xyz@scality.com";

    private static final int DEFAULT_LIST_ACCOUNTS_COUNT = 10;
    private static final int TEST_LIST_ACCOUNTS_MARKER_VAL = 2;
    private static final int TEST_LIST_ACCOUNTS_COUNT = 4;
    private static final int TEST_LIST_ACCOUNTS_COUNT_2 = -1;

    private static final String ERR_EMAIL_ADDR_INVALID = "Invalid EmailAddress";
    private static final String ERR_NAME_INVALID = "Invalid Name";
    private static final String ERR_ARN_NULL = "Arn cannot be null";
    private static final String ERR_CREATE_DATE_NULL = "CreateDate cannot be null";
    private static final String ERR_ID_NULL = "Id cannot be null";
    private static final String ERR_ID_VALUE = "Id not expected value";
    private static final String ERR_CANONICAL_ID_NULL = "CanonicalId cannot be null";
    private static final String ERR_CUSTOM_ATTRIBUTES_NULL = "Custom Attributes cannot be null";
    private static final String ERR_CUSTOM_ATTRIBUTES_IS_NULL = "Custom Attributes should be null";
    private static final String ERR_INVALID_FILTERKEY_RESPONSE = "Invalid response for filterKey";
    private static final String ERR_INVALID_FILTERKEYSTARTSWITH_RESPONSE = "Invalid response for filterKeyStartsWith";

    private static Map<String, String> customAttributes  = new HashMap<>() ;

    private static CreateAccountRequestDTO createAccountRequestDTO;
    private static final String ERR_INVALID_ACCOUNT_COUNT = "Invalid Accounts count";

    private static final ListAccountsRequestDTO defaultListAccountsRequestDTO = ListAccountsRequestDTO.builder()
            .build();

    @BeforeAll
    public static void init() throws Exception{

        basicAWSCredentials = new BasicAWSCredentials("accesskey", "secretkey");

        initCreateAccountMocks();

        initListAccountMocks();

    }

    private static void initCreateAccountMocks() {

        customAttributes.put("cd_tenant_id%3D%3Dccf2139b-7435-426b-b4d2-b917392d9540", "");
        customAttributes.put("cd_tenant_id%3D%3D27598f0b-3696-4d04-81f3-76f89b032d7d", "");

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

    private static void initListAccountMocks() {

        listAccountAmazonHttpClient = mock(AmazonHttpClient.class);
        listAccountsMockClient = new AccountServicesClient(listAccountAmazonHttpClient);

        //Default Create Account mock response
        when(listAccountAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response<ListAccountsResponseDTO> answer(InvocationOnMock invocation) {
                        Request<ListAccountsRequestDTO> ogReq = invocation.getArgument(0);
                        ListAccountsRequestDTO request = (ListAccountsRequestDTO) ogReq.getOriginalRequest();
                        String marker = request.getMarker();
                        int maxItems = request.getMaxItems();
                        String filterKey = request.getFilterKey();
                        String filterKeyStartsWith = request.getFilterKeyStartsWith();

                        Map<String, String> customAttributes1  = null ;
                        boolean withcdTenantId = false;

                        if(StringUtils.isNotBlank(filterKey)){
                            maxItems = 1;
                            customAttributes1 = new HashMap<>();
                            customAttributes1.put(filterKey,"");
                        }

                        if(StringUtils.isNotBlank(filterKeyStartsWith)){
                            withcdTenantId = true;
                        }

                        if(maxItems <= 0){
                            maxItems = DEFAULT_LIST_ACCOUNTS_COUNT; // Test Accounts count
                        }

                        int i = 0;
                        int markerVal = 0;
                        if(StringUtils.isNotBlank(marker)){
                            markerVal = Integer.parseInt(marker.substring(marker.length()-1));
                            // extracting markerVal index at last character
                        }

                        List<AccountData> accounts = new ArrayList<>();
                        // Generate Accounts with ids (markerVal + i) to maxItems count
                        for(; i < maxItems; i++){
                            AccountData data = new AccountData();
                            data.setEmailAddress(DEFAULT_EMAIL_ADDR);
                            data.setName(DEFAULT_ACCOUNT_NAME);
                            data.setArn(DEFAULT_ARN_STR + DEFAULT_ACCOUNT_NAME +"/\"");
                            data.setCreateDate(new Date());
                            data.setId(DEFAULT_ACCOUNT_ID + (i + markerVal)); //setting ID with index
                            data.setCanonicalId(DEFAULT_CANONICAL_ID);

                            if(withcdTenantId){
                                // if filterStartsWith generate customAttributes for all accounts
                                Map<String, String> customAttributestemp  = new HashMap<>() ;
                                customAttributestemp.put("cd_tenant_id%3D%3D" + UUID.randomUUID(), "");
                                data.setCustomAttributes(customAttributestemp);
                            } else {
                                data.setCustomAttributes(customAttributes1);
                            }

                            accounts.add(data);
                        }

                        ListAccountsResponseDTO response = new ListAccountsResponseDTO();
                        response.setAccounts(accounts);
                        return new Response<ListAccountsResponseDTO>(response,null);
                    }
                });
    }

    @Test
    public void testCreateAccount() throws Exception {

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
    public void testCreateAccountWithCredentials() throws Exception {

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
    public void testCreateAccountWithoutCustomAttributes() throws Exception {


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
        assertNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_IS_NULL);
    }

    @Test
    public void testCreateAccountWithQuota() throws Exception {

        CreateAccountRequestDTO createAccountRequestDTO1 = CreateAccountRequestDTO.builder()
                .emailAddress(DEFAULT_EMAIL_ADDR)
                .name(DEFAULT_ACCOUNT_NAME)
                .customAttributes(customAttributes)
                .quotaMax(10)
                .build();

        CreateAccountResponseDTO response = createAccountMockClient.createAccount(createAccountRequestDTO1).getAwsResponse();

        assertEquals(DEFAULT_EMAIL_ADDR, response.getAccount().getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccount().getData().getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccount().getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccount().getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccount().getData().getId(), ERR_ID_NULL);
        assertNotNull(response.getAccount().getData().getCanonicalId(), ERR_CANONICAL_ID_NULL);
        assertNotNull(response.getAccount().getData().getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
        assertEquals(10, response.getAccount().getData().getQuotaMax(), "Invalid QuotaMax" );
    }

    @Test
    public void testCreateAccountWithExtId() throws Exception {

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
    public void testCreateAccountRequestWithNullEmail(){

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO.builder()
                    .emailAddress(null)
                    .name(DEFAULT_ACCOUNT_NAME)
                    .customAttributes(customAttributes)
                    .build();
        }, "Expected NullPointerException");
    }

    @Test
    public void testCreateAccountRequestWithNullName(){

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO.builder()
                    .emailAddress(DEFAULT_EMAIL_ADDR)
                    .name(null)
                    .customAttributes(customAttributes)
                    .build();
        }, "Expected NullPointerException");
    }

    @Test
    public void createAccountErrorExistingAccount() throws Exception {

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
    public void testCreateAccountError400() throws Exception {

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

    /** List Accounts Test cases **/
    @Test
    public void testListAccounts() throws Exception {

        ListAccountsResponseDTO response = listAccountsMockClient.listAccounts(defaultListAccountsRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_LIST_ACCOUNTS_COUNT, response.getAccounts().size(), ERR_INVALID_ACCOUNT_COUNT);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccounts().get(0).getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccounts().get(0).getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccounts().get(0).getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccounts().get(0).getId(), ERR_ID_NULL);
        assertNotNull(response.getAccounts().get(0).getCanonicalId(), ERR_CANONICAL_ID_NULL);
    }

    @Test
    public void testListAccountsWithCredentials() throws Exception {

        accountServicesClient = new AccountServicesClient(listAccountAmazonHttpClient, basicAWSCredentials);
        ListAccountsResponseDTO response = accountServicesClient.listAccounts(defaultListAccountsRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_LIST_ACCOUNTS_COUNT, response.getAccounts().size(), ERR_INVALID_ACCOUNT_COUNT);
        assertEquals(DEFAULT_ACCOUNT_NAME, response.getAccounts().get(0).getName(), ERR_NAME_INVALID);
        assertNotNull(response.getAccounts().get(0).getArn(), ERR_ARN_NULL);
        assertNotNull(response.getAccounts().get(0).getCreateDate(), ERR_CREATE_DATE_NULL);
        assertNotNull(response.getAccounts().get(0).getId(), ERR_ID_NULL);
        assertNotNull(response.getAccounts().get(0).getCanonicalId(), ERR_CANONICAL_ID_NULL);
        init();
    }

    @Test
    public void testListAccountsWithMarker() throws Exception {

        ListAccountsRequestDTO listAccountsRequestDTO = ListAccountsRequestDTO.builder()
                .marker(DEFAULT_ACCOUNT_ID + TEST_LIST_ACCOUNTS_MARKER_VAL)
                .build();

        ListAccountsResponseDTO response = listAccountsMockClient.listAccounts(listAccountsRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_LIST_ACCOUNTS_COUNT, response.getAccounts().size(), ERR_INVALID_ACCOUNT_COUNT);
        assertEquals(DEFAULT_ACCOUNT_ID + TEST_LIST_ACCOUNTS_MARKER_VAL, response.getAccounts().get(0).getId(), ERR_ID_VALUE);
    }

    @Test
    public void testListAccountsWithMaxItems() throws Exception {

        ListAccountsRequestDTO listAccountsRequestDTO = ListAccountsRequestDTO.builder()
                .maxItems(TEST_LIST_ACCOUNTS_COUNT)
                .build();

        ListAccountsResponseDTO response = listAccountsMockClient.listAccounts(listAccountsRequestDTO).getAwsResponse();

        assertEquals(TEST_LIST_ACCOUNTS_COUNT, response.getAccounts().size(), ERR_INVALID_ACCOUNT_COUNT);
    }

    @Test
    public void testListAccountsWithMaxItemsAndMarker() throws Exception {

        ListAccountsRequestDTO listAccountsRequestDTO = ListAccountsRequestDTO.builder()
                .marker(DEFAULT_ACCOUNT_ID + TEST_LIST_ACCOUNTS_MARKER_VAL)
                .maxItems(TEST_LIST_ACCOUNTS_COUNT)
                .build();

        ListAccountsResponseDTO response = listAccountsMockClient.listAccounts(listAccountsRequestDTO).getAwsResponse();

        assertEquals(TEST_LIST_ACCOUNTS_COUNT, response.getAccounts().size(), ERR_INVALID_ACCOUNT_COUNT);
        assertEquals(DEFAULT_ACCOUNT_ID + TEST_LIST_ACCOUNTS_MARKER_VAL, response.getAccounts().get(0).getId(), ERR_ID_VALUE);
    }

    @Test
    public void testListAccountsWithFilterKey() throws Exception {

        String customAttributeKey = customAttributes.keySet().iterator().next();
        ListAccountsRequestDTO listAccountsRequestDTO = ListAccountsRequestDTO.builder()
                .filterKey(customAttributeKey)
                .build();

        ListAccountsResponseDTO response = listAccountsMockClient.listAccounts(listAccountsRequestDTO).getAwsResponse();

        assertEquals(1, response.getAccounts().size(), ERR_INVALID_ACCOUNT_COUNT);
        assertNotNull(response.getAccounts().get(0).getCustomAttributes(), ERR_CUSTOM_ATTRIBUTES_NULL);
        assertEquals(customAttributeKey, response.getAccounts().get(0).getCustomAttributes().keySet().iterator().next(), ERR_INVALID_FILTERKEY_RESPONSE);
    }

    @Test
    public void testListAccountsWithFilterKeyStartsWith() throws Exception {

        ListAccountsRequestDTO listAccountsRequestDTO = ListAccountsRequestDTO.builder()
                .filterKeyStartsWith("cd_tenant_id")
                .build();

        ListAccountsResponseDTO response = listAccountsMockClient.listAccounts(listAccountsRequestDTO).getAwsResponse();

        assertNotEquals(0, response.getAccounts().size(), ERR_INVALID_ACCOUNT_COUNT);
        assertNotNull(response.getAccounts().get(0).getCustomAttributes(), ERR_INVALID_FILTERKEYSTARTSWITH_RESPONSE);
    }

    @Test
    public void testListAccountsWithInvalidMaxItems(){
        ListAccountsRequestDTO listAccountsRequestDTO = ListAccountsRequestDTO.builder()
                .maxItems(TEST_LIST_ACCOUNTS_COUNT_2)
                .build();

        assertThrows(SdkClientException.class, () -> {
            listAccountsMockClient.listAccounts(listAccountsRequestDTO).getAwsResponse();
        }, "Expected SdkClientException");
    }

    @Test
    public void testListAccountsWithNonNullEmptyMarker(){
        ListAccountsRequestDTO listAccountsRequestDTO = ListAccountsRequestDTO.builder()
                .marker("")
                .build();

        assertThrows(SdkClientException.class, () -> {
            listAccountsMockClient.listAccounts(listAccountsRequestDTO).getAwsResponse();
        }, "Expected SdkClientException");
    }

    @Test
    public void testListAccountsFilterKeyAndFilterKeyStartsW(){
        ListAccountsRequestDTO listAccountsRequestDTO = ListAccountsRequestDTO.builder()
                .filterKey(customAttributes.keySet().iterator().next())
                .filterKeyStartsWith("cd_tenant_id")
                .build();

        assertThrows(SdkClientException.class, () -> {
            listAccountsMockClient.listAccounts(listAccountsRequestDTO).getAwsResponse();
        }, "Expected SdkClientException");
    }

    /** Tests with Actual Vault **/
    @Disabled
    @Test
    @SuppressWarnings( "deprecation" )
    public void testCreateAccountWithActualVault() {
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

    @Disabled
    @Test
    @SuppressWarnings( "deprecation" )
    public void testListAccountsWithActualVault() {
        //"D4IT2AWSB588GO5J9T00": "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"
        AccountServicesClient amazonIdentityManagementClient = new AccountServicesClient(
                new BasicAWSCredentials("D4IT2AWSB588GO5J9T00", "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"));

        amazonIdentityManagementClient.setEndpoint("http://localhost:8600");

        ListAccountsRequestDTO listAccountsRequestDTO = ListAccountsRequestDTO.builder()
                .maxItems(2)
                .marker("699335213475")
                .filterKeyStartsWith("cd_tenant_id")
//                .filterKey("cd_tenant_id%3D%3Dccf2139b-7435-426b-b4d2-b917392d9540")
                .build();
        ListAccountsResponseDTO response = amazonIdentityManagementClient.listAccounts(listAccountsRequestDTO).getAwsResponse();
        assertNotNull(response.getAccounts().size(), "List Accounts empty");
        assertNotNull(response.getAccounts().get(0).getId(), "List Accounts ID empty");
        assertNotNull(response.getAccounts().get(0).getCustomAttributes(), "List Accounts customAttributes empty");
    }

}
