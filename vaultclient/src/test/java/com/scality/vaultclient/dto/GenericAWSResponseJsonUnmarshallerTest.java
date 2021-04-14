package com.scality.vaultclient.dto;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.JsonUnmarshallerContextImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Date;

import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_ACCESS_KEY;
import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_ASSUMED_USER_ARN;
import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_SECRET_KEY;
import static com.scality.vaultclient.utils.VaultServicesTestConstants.TEST_SESSION_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GenericAWSResponseJsonUnmarshallerTest {

    private GenericAWSResponseJsonUnmarshaller genericResponseJsonUnmarshallerUnderTest;

    @BeforeEach
    void setUp() {
        genericResponseJsonUnmarshallerUnderTest = new GenericAWSResponseJsonUnmarshaller<>(AssumeRoleResult.class);
    }

    @Test
    void testUnmarshallWithDate() {
        // Setup

        Date currDate = new Date();

        String inputString ="{\n" +
                "        \"Credentials\": {\n" +
                "            \"AccessKeyId\": \""+ TEST_ACCESS_KEY +"\",\n" +
                "            \"SecretAccessKey\": \""+ TEST_SECRET_KEY +"\",\n" +
                "            \"SessionToken\": \""+ TEST_SESSION_TOKEN +"\",\n" +
                "            \"Expiration\":" + currDate.getTime() + "\n" +
                "        },\n" +
                "        \"AssumedRoleUser\": \"" + TEST_ASSUMED_USER_ARN + "\"\n" +
                "    }";

        final JsonUnmarshallerContext contextMock = mock(JsonUnmarshallerContextImpl.class);

        final HttpResponse httpResponseMock = mock(HttpResponse.class);

        when(httpResponseMock.getContent()).thenReturn(new ByteArrayInputStream(inputString.getBytes(Charset.forName("UTF-8"))));

        when(contextMock.getHttpResponse()).thenReturn(httpResponseMock);

        // Run the test

        final AssumeRoleResult response = (AssumeRoleResult) genericResponseJsonUnmarshallerUnderTest.unmarshall(contextMock);

        // Verify the results
        assertNotNull(response.getCredentials());
        assertEquals(currDate, response.getCredentials().getExpiration());
        assertNotNull(response.getAssumedRoleUser());

    }
}
