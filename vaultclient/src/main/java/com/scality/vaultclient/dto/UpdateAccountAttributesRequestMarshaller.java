package com.scality.vaultclient.dto;

import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.scality.vaultclient.services.VaultClientException;

/**
 * The type Update account request marshaller extends GenericRequestMarshaller to marshall Update Account Requests.
 */
public class UpdateAccountAttributesRequestMarshaller extends GenericRequestMarshaller<UpdateAccountAttributesRequestDTO>{

    private static final MarshallingInfo<String> NAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("name").build();
    private static final MarshallingInfo<String> CUSTOM_ATTRIBUTES_BINDING = MarshallingInfo.builder(MarshallingType.JSON_VALUE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("customAttributes").build();
    private static final String UPDATE_ACCOUNT_ATTRIBUTES_ACTION = "UpdateAccountAttributes";

    private static final UpdateAccountAttributesRequestMarshaller instance = new UpdateAccountAttributesRequestMarshaller();

    /**
     * static factory method that returns a singleton instance of the UpdateAccountAttributesRequestMarshaller class.
     *
     * @return instance of UpdateAccountAttributesRequestMarshaller
     */
    public static UpdateAccountAttributesRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Binds protocolMarshaller with updateAccountAttributesRequestDTO parameters to marshall.
     *
     * @param updateAccountAttributesRequestDTO         the request dto of type UpdateAccountAttributesRequestDTO
     * @param protocolMarshaller the protocol marshaller
     */
    @Override
    public void marshall(UpdateAccountAttributesRequestDTO updateAccountAttributesRequestDTO, ProtocolRequestMarshaller<UpdateAccountAttributesRequestDTO> protocolMarshaller) {

        super.marshall(updateAccountAttributesRequestDTO, protocolMarshaller, UPDATE_ACCOUNT_ATTRIBUTES_ACTION);

        if (StringUtils.isNullOrEmpty(updateAccountAttributesRequestDTO.getName())) {
            throw new VaultClientException("accountName is required.");
        }

        try {
            protocolMarshaller.marshall(updateAccountAttributesRequestDTO.getName(), NAME_BINDING);

            if (updateAccountAttributesRequestDTO.getCustomAttributes() != null) {
                protocolMarshaller.marshall(new Gson().toJson(updateAccountAttributesRequestDTO.getCustomAttributes()), CUSTOM_ATTRIBUTES_BINDING);
            } else {
                throw new VaultClientException("CustomAttributes is required in Update Account Attributes.");
            }
        } catch (Exception e) {
            throw new VaultClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }
}
