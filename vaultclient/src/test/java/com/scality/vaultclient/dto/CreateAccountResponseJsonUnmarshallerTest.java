package com.scality.vaultclient.dto;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateAccountResponseJsonUnmarshallerTest {

    private GenericResponseJsonUnmarshaller<CreateAccountResponseDTO> createAccountResponseJsonUnmarshallerUnderTest;

    @BeforeEach
    void setUp() {
        createAccountResponseJsonUnmarshallerUnderTest = new GenericResponseJsonUnmarshaller<CreateAccountResponseDTO>(CreateAccountResponseDTO.class);
    }

    @Test
    void testUnmarshall() throws Exception {
        // Setup
        final CreateAccountResponseDTO expectedResult = new CreateAccountResponseDTO();
        final Account account = new Account();
        final AccountData data = new AccountData();
        data.setArn("arn");
        data.setName("name");
        data.setEmailAddress("emailAddress");
        data.setId("id");
        data.setQuotaMax(100);
        data.setCreateDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());
        data.setCanonicalId("canonicalId");
        data.setAliasList(Arrays.asList("value"));
        data.setOidcpList(Arrays.asList("value"));
        account.setData(data);
        expectedResult.setAccount(account);

        final JsonUnmarshallerContext context = mock(JsonUnmarshallerContext.class);
        when(context.getHttpResponse())
                .thenAnswer(new Answer<HttpResponse>() {
                    @Override
                    public HttpResponse answer(InvocationOnMock invocation) {
                        HttpResponse httpsResponse = new HttpResponse(null, null);
                        httpsResponse.setContent(new ByteArrayInputStream(new Gson().toJson(expectedResult).getBytes(Charset.forName("UTF-8"))));
                        return httpsResponse;
                    }
                });

        // Run the test
        final CreateAccountResponseDTO result = createAccountResponseJsonUnmarshallerUnderTest.unmarshall(context);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUnmarshall_ThrowsException() {
        // Setup
        final JsonUnmarshallerContext context = null;

        // Run the test
        assertThrows(Exception.class, () -> createAccountResponseJsonUnmarshallerUnderTest.unmarshall(context));
    }

    public GenericResponseJsonUnmarshaller<CreateAccountResponseDTO> getCreateAccountResponseJsonUnmarshallerUnderTest() {
        return createAccountResponseJsonUnmarshallerUnderTest;
    }

    public void setCreateAccountResponseJsonUnmarshallerUnderTest(GenericResponseJsonUnmarshaller<CreateAccountResponseDTO> createAccountResponseJsonUnmarshallerUnderTest) {
        this.createAccountResponseJsonUnmarshallerUnderTest = createAccountResponseJsonUnmarshallerUnderTest;
    }
}
