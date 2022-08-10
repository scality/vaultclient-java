package com.scality.vaultclient.dto;

import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.amazonaws.util.StringUtils;
import com.scality.vaultclient.services.VaultClientException;

/**
 * The type Create account request marshaller extends GenericRequestMarshaller to marshall Create Account Requests.
 */
public class GetUserByAccessKeyRequestMarshaller extends GenericRequestMarshaller<GetUserByAccessKeyRequestDTO>{

    private static final MarshallingInfo<String> ACCESS_KEY_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("accessKey").build();
    private static final String GET_USER_BY_ACCESS_KEY_ACTION = "GetUserByAccessKey";

    private static final GetUserByAccessKeyRequestMarshaller instance = new GetUserByAccessKeyRequestMarshaller();

    public static GetUserByAccessKeyRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Binds protocolMarshaller with createAccountRequestDTO parameters to marshall.
     *
     * @param getUserByAccessKeyRequestDTO         the request dto of type GetUserByAccessKeyRequestDTO
     * @param protocolMarshaller the protocol marshaller
     */
    @Override
    public void marshall(GetUserByAccessKeyRequestDTO getUserByAccessKeyRequestDTO, ProtocolRequestMarshaller<GetUserByAccessKeyRequestDTO> protocolMarshaller) {

        super.marshall(getUserByAccessKeyRequestDTO, protocolMarshaller, GET_USER_BY_ACCESS_KEY_ACTION);

        if (StringUtils.isNullOrEmpty(getUserByAccessKeyRequestDTO.getAccessKey())) {
            throw new VaultClientException("accessKey is required.");
        }

        try {
            protocolMarshaller.marshall(getUserByAccessKeyRequestDTO.getAccessKey(), ACCESS_KEY_BINDING);
        } catch (Exception e) {
            throw new VaultClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }
}
