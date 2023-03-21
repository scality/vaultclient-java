package com.scality.vaultclient.dto;

import com.amazonaws.Request;
import com.amazonaws.SdkClientException;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.protocol.OperationInfo;
import com.amazonaws.protocol.Protocol;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.amazonaws.protocol.json.SdkJsonProtocolFactory;
import com.amazonaws.transform.Marshaller;

/**
 * A GenericRequestProtocolMarshaller that implements Marshaller
 */
@SuppressWarnings("unchecked")
public class GenericRequestProtocolMarshaller<T> implements Marshaller<Request<T>,T> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.AWS_JSON).requestUri("/")
            .httpMethodName(HttpMethodName.POST).hasExplicitPayloadMember(false).hasPayloadMembers(false).serviceName("Vault").build();

    private final SdkJsonProtocolFactory protocolFactory;

    private final GenericRequestMarshaller<T> requestMarshaller;

    /**
     * This class implements the Marshaller interface and is responsible for marshalling an object of type T
     * into an AWS request using the SdkJsonProtocolFactory. It uses the GenericRequestMarshaller to marshall the input
     * object into a payload that can be transmitted over the network. This class also creates an OperationInfo object
     * that specifies the operation details, such as the httpMethodName, requestUri, hasExplicitPayloadMember, hasPayloadMembers,
     * and serviceName.
     *
     * This class is designed to be used with the AWS SDK for Java to create a request that can be sent to an AWS service.
     *
     * @param protocolFactory an instance of SdkJsonProtocolFactory
     * @param requestMarshaller an instance of GenericRequestMarshaller
     */
    public GenericRequestProtocolMarshaller(SdkJsonProtocolFactory protocolFactory, GenericRequestMarshaller requestMarshaller) {
        this.protocolFactory = protocolFactory;
        this.requestMarshaller = requestMarshaller;
    }

    /**
     * Instantiates protocolMarshaller and marshals request dto with bindings in to Amazon Web Service Request object
     *
     * @param request   the request dto
     * @return Returns the Amazon Web Service Request object
     */
    @Override
    public Request<T> marshall(T request) {

        if (request == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<T> protocolMarshaller = protocolFactory.createProtocolMarshaller(SDK_OPERATION_BINDING,
                    request);

            protocolMarshaller.startMarshalling();
            requestMarshaller.marshall( request, protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

    /**
     * Getter for protocolFactory
     *
     * @return Returns the protocolFactory
     */
    public SdkJsonProtocolFactory getProtocolFactory() {
        return protocolFactory;
    }
}
