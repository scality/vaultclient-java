package com.scality.vaultclient.services;

import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.scality.vaultclient.dto.AssumeRoleResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.util.Date;

import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_ACCESS_KEY;
import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_ASSUMED_USER_ARN;
import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_ROLE_ARN;
import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_SECRET_KEY;
import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_SESSION_NAME;
import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_SESSION_TOKEN;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssumeRoleBackbeatServiceTest {

    // mock vault client
    protected static AmazonHttpClient amazonHttpClient;

    // dummy Aws credentials
    protected static BasicAWSCredentials basicAWSCredentials;

    // Service
    protected static SecurityTokenServicesClient securityTokenServicesClientMock;

    // Service with aws creds
    protected static SecurityTokenServicesClient securityTokenServicesClient;


    private static final AssumeRoleRequest defaultAssumeRoleRequest = new AssumeRoleRequest();

    @BeforeAll
    public static void init(){

        basicAWSCredentials = new BasicAWSCredentials("accesskey", "secretkey");

        defaultAssumeRoleRequest.setRoleArn(TEST_ROLE_ARN);
        defaultAssumeRoleRequest.setRoleSessionName(TEST_SESSION_NAME);

        initMocks();
    }

    private static void initMocks() {

        amazonHttpClient = mock(AmazonHttpClient.class);
        securityTokenServicesClientMock = new SecurityTokenServicesClient(amazonHttpClient);

        //Default Assume Role Backbeat mock response
        when(amazonHttpClient.execute(any(Request.class), any(), any(), any(),any()))
                .thenAnswer((Answer<Response>) invocation -> {

                    Credentials credentials = new Credentials();
                    credentials.setAccessKeyId(TEST_ACCESS_KEY);
                    credentials.setSecretAccessKey(TEST_SECRET_KEY);
                    credentials.setExpiration(new Date());
                    credentials.setSessionToken(TEST_SESSION_TOKEN);

                    AssumeRoleResult response = new AssumeRoleResult();
                    response.setCredentials(credentials);
                    response.setAssumedRoleUser(TEST_ASSUMED_USER_ARN);
                    return new Response<>(response,null);
                });
    }

    @Test
    public void testAssumeRoleBackbeat() {

        AssumeRoleResult response = securityTokenServicesClientMock.assumeRoleBackbeat(defaultAssumeRoleRequest).getAwsResponse();

        assertNotNull(response.getCredentials(), "Credentials empty");
        assertNotNull(response.getCredentials().getExpiration(), "Credentials expiration empty");
        assertNotNull(response.getCredentials().getAccessKeyId(), "Credentials access key empty");
        assertNotNull(response.getCredentials().getSecretAccessKey(), "Credentials secret key empty");
        assertNotNull(response.getCredentials().getSessionToken(), "Credentials session token empty");
        assertNotNull(response.getAssumedRoleUser(), "AssumedRoleUser ARN empty");
    }

    @Test
    public void testAssumeRoleBackbeatWithCredentials() {

        securityTokenServicesClient = new SecurityTokenServicesClient(amazonHttpClient, basicAWSCredentials);
        AssumeRoleResult response = securityTokenServicesClient.assumeRoleBackbeat(defaultAssumeRoleRequest).getAwsResponse();

        assertNotNull(response.getCredentials(), "Credentials empty");
        assertNotNull(response.getCredentials().getExpiration(), "Credentials expiration empty");
        assertNotNull(response.getCredentials().getAccessKeyId(), "Credentials access key empty");
        assertNotNull(response.getCredentials().getSecretAccessKey(), "Credentials secret key empty");
        assertNotNull(response.getCredentials().getSessionToken(), "Credentials session token empty");
        assertNotNull(response.getAssumedRoleUser(), "AssumedRoleUser ARN empty");
        init();
    }

    @Test
    public void testAssumeRoleBackbeatInvalidRoleARN(){
        AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest();
        assumeRoleRequest.setRoleSessionName(TEST_SESSION_NAME);

        assertThrows(SdkClientException.class, () -> securityTokenServicesClientMock.assumeRoleBackbeat(assumeRoleRequest), "Expected SdkClientException");
    }

    @Test
    public void testAssumeRoleBackbeatInvalidRoleSessionName(){
        AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest();
        assumeRoleRequest.setRoleArn(TEST_ROLE_ARN);

        assertThrows(SdkClientException.class, () -> securityTokenServicesClientMock.assumeRoleBackbeat(assumeRoleRequest), "Expected SdkClientException");
    }

    /** Tests with Actual Vault **/
    @Disabled
    @Test
    @SuppressWarnings( "deprecation" )
    public void testAssumeRoleBackbeatWithActualVault() {
        //"D4IT2AWSB588GO5J9T00": "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"
        SecurityTokenServicesClient amazonIdentityManagementClient = new SecurityTokenServicesClient(
                new BasicAWSCredentials("D4IT2AWSB588GO5J9T00", "UEEu8tYlsOGGrgf4DAiSZD6apVNPUWqRiPG0nTB6"));

        amazonIdentityManagementClient.setEndpoint("http://localhost:8500");

        AssumeRoleRequest request= new AssumeRoleRequest();
        request.setRoleArn("arn:aws:iam::710685663830:role/osis");
        request.setRoleSessionName("session1234");

        AssumeRoleResult response = amazonIdentityManagementClient.assumeRoleBackbeat(request).getAwsResponse();
        assertNotNull(response.getCredentials(), "Credentials empty");
        assertNotNull(response.getCredentials().getExpiration(), "Credentials expiration empty");
        assertNotNull(response.getCredentials().getAccessKeyId(), "Credentials access key empty");
        assertNotNull(response.getCredentials().getSecretAccessKey(), "Credentials secret key empty");
        assertNotNull(response.getCredentials().getSessionToken(), "Credentials session token empty");
        assertNotNull(response.getAssumedRoleUser(), "AssumedRoleUser ARN empty");
    }

}
