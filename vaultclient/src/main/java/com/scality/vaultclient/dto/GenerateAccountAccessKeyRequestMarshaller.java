package com.scality.vaultclient.dto;

import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.amazonaws.util.StringUtils;
import com.scality.vaultclient.services.VaultClientException;

/**
 * The type generate account access key request marshaller extends GenericRequestMarshaller to marshall Generate Account AccessKey Requests.
 */
public class GenerateAccountAccessKeyRequestMarshaller extends GenericRequestMarshaller<GenerateAccountAccessKeyRequest>{

    private static final MarshallingInfo<String> ACCOUNT_NAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("AccountName").build();
    private static final MarshallingInfo<String> EXTERNAL_ACCESS_KEY_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("externalAccessKey").build();
    private static final MarshallingInfo<String> EXTERNAL_SECRET_KEY_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("externalSecretKey").build();
    private static final MarshallingInfo<Long> DURATION_SECONDS_BINDING = MarshallingInfo.builder(MarshallingType.LONG).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("DurationSeconds").build();


    private static final String GENERATE_ACCOUNT_ACCESS_KEY_ACTION = "GenerateAccountAccessKey";

    private static final GenerateAccountAccessKeyRequestMarshaller instance = new GenerateAccountAccessKeyRequestMarshaller();

    /**
     * static factory method that returns a singleton instance of the GenerateAccountAccessKeyRequestMarshaller class.
     *
     * @return instance of GenerateAccountAccessKeyRequestMarshaller
     */
    public static GenerateAccountAccessKeyRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Binds protocolMarshaller with generateAccountAccessKeyRequest parameters to marshall.
     *
     * @param generateAccountAccessKeyRequest         the request dto of type GenerateAccountAccessKeyRequest
     * @param protocolMarshaller the protocol marshaller
     */
    @Override
    public void marshall(GenerateAccountAccessKeyRequest generateAccountAccessKeyRequest, ProtocolRequestMarshaller<GenerateAccountAccessKeyRequest> protocolMarshaller) {

        super.marshall(generateAccountAccessKeyRequest, protocolMarshaller, GENERATE_ACCOUNT_ACCESS_KEY_ACTION);

        try {

            if (StringUtils.isNullOrEmpty(generateAccountAccessKeyRequest.getAccountName())) {
                throw new VaultClientException("invalid account name supplied.");
            }

            protocolMarshaller.marshall(generateAccountAccessKeyRequest.getAccountName(), ACCOUNT_NAME_BINDING);

            if (null != generateAccountAccessKeyRequest.getExternalAccessKey()) {

                if (StringUtils.isNullOrEmpty(generateAccountAccessKeyRequest.getExternalAccessKey())) {
                    throw new VaultClientException("invalid external access key supplied");
                }

                protocolMarshaller.marshall(generateAccountAccessKeyRequest.getExternalAccessKey(), EXTERNAL_ACCESS_KEY_BINDING);
            }

            if (null != generateAccountAccessKeyRequest.getExternalSecretKey()) {

                if (StringUtils.isNullOrEmpty(generateAccountAccessKeyRequest.getExternalSecretKey())) {
                    throw new VaultClientException("invalid external secret key supplied");
                }

                protocolMarshaller.marshall(generateAccountAccessKeyRequest.getExternalSecretKey(), EXTERNAL_SECRET_KEY_BINDING);
            }

            if (generateAccountAccessKeyRequest.getDurationSeconds() < 0) {
                throw new VaultClientException("invalid duration seconds supplied");
            } else if (generateAccountAccessKeyRequest.getDurationSeconds() > 0) {
                protocolMarshaller.marshall(generateAccountAccessKeyRequest.getDurationSeconds(), DURATION_SECONDS_BINDING);
            }

        } catch (Exception e) {
            throw new VaultClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }
}
