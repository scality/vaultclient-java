package com.scality.vaultclient.dto;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolMarshaller;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.scality.vaultclient.services.VaultClientException;

/**
 * The type GenericRequestMarshaller to marshall common bindings to the AWS request object.
 */
public abstract class GenericRequestMarshaller<T> {

    private static final MarshallingInfo<String> ACTION_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("Action").build();
    private static final MarshallingInfo<String> VERSION_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("Version").build();
    private static final MarshallingInfo<String> JSON_HEADER_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.HEADER)
            .marshallLocationName("Content-Type").build();
    private static final MarshallingInfo<String> SIGNATURE_HEADER_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.HEADER)
            .marshallLocationName("x-amz-content-sha256").build();

    /**
     * Marshalls protocolMarshaller with common bindings
     *
     * @param request         the request dto
     * @param protocolMarshaller    the protocol marshaller
     * @param action    the action to be performed
     */
    public void marshall(AmazonWebServiceRequest request, ProtocolMarshaller protocolMarshaller, String action) {

        if (request == null) {
            throw new VaultClientException("Invalid argument passed to marshall(...).");
        }

        try {
            protocolMarshaller.marshall(action, ACTION_BINDING);
            protocolMarshaller.marshall("2010-05-08", VERSION_BINDING);
            protocolMarshaller.marshall("application/json", JSON_HEADER_BINDING);
            protocolMarshaller.marshall("required", SIGNATURE_HEADER_BINDING);
        } catch (Exception e) {
            throw new VaultClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Binds protocolMarshaller with requestDTO parameters to marshall.
     *
     * @param requestDTO         the request dto
     * @param protocolMarshaller    the protocol marshaller
     */
    public abstract void marshall(T requestDTO, ProtocolRequestMarshaller<T> protocolMarshaller);
}
