package com.scality.vaultclient.services;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.scality.vaultclient.dto.AccountData;
import com.scality.vaultclient.dto.ListAccountsRequestDTO;
import com.scality.vaultclient.dto.ListAccountsResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.scality.vaultclient.utils.VaultServicesTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListAccountsServiceTest {

    // mock vault client
    protected static AmazonHttpClient listAccountAmazonHttpClient;

    // dummy Aws credentials
    protected static BasicAWSCredentials basicAWSCredentials;

    // Service
    protected static AccountServicesClient listAccountsMockClient;

    // Service with aws creds
    protected static AccountServicesClient accountServicesClient;

    private static Map<String, String> customAttributes  = new HashMap<>() ;

    private static final ListAccountsRequestDTO defaultListAccountsRequestDTO = ListAccountsRequestDTO.builder()
            .build();

    @BeforeAll
    public static void init() throws Exception{

        basicAWSCredentials = new BasicAWSCredentials("accesskey", "secretkey");

        customAttributes.put("cd_tenant_id%3D%3Dccf2139b-7435-426b-b4d2-b917392d9540", "");
        customAttributes.put("cd_tenant_id%3D%3D27598f0b-3696-4d04-81f3-76f89b032d7d", "");

        initListAccountMocks();

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
