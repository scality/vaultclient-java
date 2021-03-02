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
 * CreateAccountRequestProtocolMarshaller Marshaller
 */
public class CreateAccountRequestProtocolMarshaller implements Marshaller<Request<CreateAccountRequestDTO>, CreateAccountRequestDTO> {

    private static final OperationInfo SDK_OPERATION_BINDING = OperationInfo.builder().protocol(Protocol.AWS_JSON).requestUri("/")
            .httpMethodName(HttpMethodName.POST).hasExplicitPayloadMember(false).hasPayloadMembers(false).serviceName("Vault").build();

    private final SdkJsonProtocolFactory protocolFactory;

    public CreateAccountRequestProtocolMarshaller(SdkJsonProtocolFactory protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    @Override
    public Request<CreateAccountRequestDTO> marshall(CreateAccountRequestDTO createAccountRequestDTO) {

        if (createAccountRequestDTO == null) {
            throw new SdkClientException("Invalid argument passed to marshall(...)");
        }

        try {
            final ProtocolRequestMarshaller<CreateAccountRequestDTO> protocolMarshaller = protocolFactory.createProtocolMarshaller(SDK_OPERATION_BINDING,
                    createAccountRequestDTO);

            protocolMarshaller.startMarshalling();
            CreateAccountRequestMarshaller.getInstance().marshall(createAccountRequestDTO, protocolMarshaller);
            return protocolMarshaller.finishMarshalling();
        } catch (Exception e) {
            throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }

    public SdkJsonProtocolFactory getProtocolFactory() {
        return protocolFactory;
    }
}
