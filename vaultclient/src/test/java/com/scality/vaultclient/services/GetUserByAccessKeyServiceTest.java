package com.scality.vaultclient.services;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.scality.vaultclient.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;

import static com.scality.vaultclient.utils.VaultServicesTestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetUserByAccessKeyServiceTest {

    // mock vault client
    protected static AmazonHttpClient getUserByAccessKeyAmazonHttpClient;

    // dummy Aws credentials
    protected static BasicAWSCredentials basicAWSCredentials;

    // Service
    protected static AccountServicesClient getUserByAccessKeyMockClient;

    @BeforeAll
    public static void init() throws Exception{

        basicAWSCredentials = new BasicAWSCredentials("accesskey", "secretkey");

        initGetUserByAccessKeyMocks();

    }

    private static void initGetUserByAccessKeyMocks() {

        getUserByAccessKeyAmazonHttpClient = mock(AmazonHttpClient.class);
        getUserByAccessKeyMockClient = new AccountServicesClient(getUserByAccessKeyAmazonHttpClient);

        //Default Get Account mock response
        when(getUserByAccessKeyAmazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer(new Answer<Response>() {
                    @Override
                    public Response<GetUserByAccessKeyResponseDTO> answer(InvocationOnMock invocation) {

                        UserData data = new UserData();
                        data.setEmailAddress(DEFAULT_USER_EMAIL_ADDR);
                        data.setName(DEFAULT_USER_NAME);
                        data.setArn(DEFAULT_USER_ARN_STR);
                        data.setCreateDate(new Date());
                        data.setId(DEFAULT_USER_ID);
                        final GetUserByAccessKeyResponseDTO response = new GetUserByAccessKeyResponseDTO();
                        response.setData(data);
                        return new Response<GetUserByAccessKeyResponseDTO>(response,null);
                    }
                });
    }

    /** Get AccountData Test cases **/
    @Test
    public void testGetUserByAccessKey() throws Exception {
        GetUserByAccessKeyRequestDTO getUserByAccessKeyRequestDTO = GetUserByAccessKeyRequestDTO.builder()
                .accessKey(TEST_ACCESS_KEY)
                .build();

        GetUserByAccessKeyResponseDTO response = getUserByAccessKeyMockClient.getUserByAccessKey(getUserByAccessKeyRequestDTO).getAwsResponse();

        assertEquals(DEFAULT_USER_EMAIL_ADDR, response.getData().getEmailAddress(), ERR_EMAIL_ADDR_INVALID);
        assertEquals(DEFAULT_USER_NAME, response.getData().getName(), ERR_NAME_INVALID);
        assertEquals(DEFAULT_USER_ARN_STR, response.getData().getArn(), ERR_ARN_NULL);
        assertNotNull(response.getData().getCreateDate(), ERR_CREATE_DATE_NULL);
        assertEquals(DEFAULT_USER_ID, response.getData().getId(), ERR_ID_NULL);
    }

    @Test
    public void testGetUserByAccessKeyWithInvalidRequest(){
        GetUserByAccessKeyRequestDTO getUserByAccessKeyRequestDTO = GetUserByAccessKeyRequestDTO.builder()
                .accessKey("")
                .build();

        assertThrows(SdkClientException.class, () -> {
            getUserByAccessKeyMockClient.getUserByAccessKey(getUserByAccessKeyRequestDTO).getAwsResponse();
        }, "Expected SdkClientException");
    }
}
