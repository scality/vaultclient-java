package com.scality.vaultclient.dto;

import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.google.gson.Gson;
import com.scality.vaultclient.services.VaultClientException;

/**
 * The type add account request marshaller extends GenericRequestMarshaller to marshall add Account Requests.
 */
public class AddAccountAttributesRequestMarshaller extends GenericRequestMarshaller<AddAccountAttributesRequestDTO>{

    private static final MarshallingInfo<String> REQ_PARAMS_BINDING = MarshallingInfo.builder(MarshallingType.JSON_VALUE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("reqParams").build();
    private static final String ADD_ACCOUNT_ATTRIBUTES_ACTION = "AddAccountAttributes";

    private static final AddAccountAttributesRequestMarshaller instance = new AddAccountAttributesRequestMarshaller();

    public static AddAccountAttributesRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Binds protocolMarshaller with addAccountAttributesRequestDTO parameters to marshall.
     *
     * @param addAccountAttributesRequestDTO         the request dto of type addAccountAttributesRequestDTO
     * @param protocolMarshaller the protocol marshaller
     */
    @Override
    public void marshall(AddAccountAttributesRequestDTO addAccountAttributesRequestDTO, ProtocolRequestMarshaller<AddAccountAttributesRequestDTO> protocolMarshaller) {

        super.marshall(addAccountAttributesRequestDTO, protocolMarshaller, ADD_ACCOUNT_ATTRIBUTES_ACTION);

        try {
            if (!addAccountAttributesRequestDTO.getReqParams().isEmpty()) {
                protocolMarshaller.marshall(new Gson().toJson(addAccountAttributesRequestDTO.getReqParams()), REQ_PARAMS_BINDING);
            } else {
                throw new VaultClientException("reqParams is required in Add Account Attributes.");
            }
        } catch (Exception e) {
            throw new VaultClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }
}
