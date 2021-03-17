package com.scality.vaultclient.dto;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.protocol.ProtocolMarshaller;
import com.scality.vaultclient.services.VaultClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenericRequestMarshallerTest {

    private GenericRequestMarshaller genericRequestMarshallerUnderTest;

    @BeforeEach
    public void setUp() {
        // Using CreateAccountRequestMarshaller as an example to test GenericRequestMarshaller
        genericRequestMarshallerUnderTest = new CreateAccountRequestMarshaller();
    }

    @Test
    void testMarshallNullRequest() {
        // Setup
        final AmazonWebServiceRequest request = null;
        final ProtocolMarshaller protocolMarshaller = null;

        // Verify the results
        VaultClientException e = assertThrows(VaultClientException.class, () -> {
            genericRequestMarshallerUnderTest.marshall(request, protocolMarshaller, "action");
        }, "Expected VaultClientException");

        assertEquals("Invalid argument passed to marshall(...).", e.getErrorMessage(), "Invalid error message");
    }

    @Test
    void testMarshallNullMarshaller() {
        // Setup
        final AmazonWebServiceRequest request = new AmazonWebServiceRequest() {
        };
        final ProtocolMarshaller protocolMarshaller = null;

        // Verify the results
        VaultClientException e = assertThrows(VaultClientException.class, () -> {
            genericRequestMarshallerUnderTest.marshall(request, protocolMarshaller, "action");
        }, "Expected VaultClientException");
        assertTrue(e.getErrorMessage().startsWith("Unable to marshall request to JSON: "), "Invalid error message");
    }

    public GenericRequestMarshaller getGenericRequestMarshallerUnderTest() {
        return genericRequestMarshallerUnderTest;
    }

    public void setGenericRequestMarshallerUnderTest(GenericRequestMarshaller genericRequestMarshallerUnderTest) {
        this.genericRequestMarshallerUnderTest = genericRequestMarshallerUnderTest;
    }
}
