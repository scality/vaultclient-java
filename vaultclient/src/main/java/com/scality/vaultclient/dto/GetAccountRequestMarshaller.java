package com.scality.vaultclient.dto;

import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.amazonaws.util.StringUtils;
import com.scality.vaultclient.services.VaultClientException;

/**
 * The type Get account request marshaller extends GenericRequestMarshaller to marshall Get Account Requests.
 */
public class GetAccountRequestMarshaller extends GenericRequestMarshaller<GetAccountRequestDTO>{

    private static final MarshallingInfo<String> ACCOUNT_NAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("accountName").build();
    private static final MarshallingInfo<String> ACCOUNT_ID_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("accountId").build();
    private static final MarshallingInfo<String> ACCOUNT_ARN_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("accountArn").build();
    private static final MarshallingInfo<String> CANONICAL_ID_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("canonicalId").build();
    private static final MarshallingInfo<String> EMAIL_ADDRESS_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("emailAddress").build();

    private static final int MAX_NUMBER_OF_REQUEST_PARAMETERS = 1;

    private static final String GET_ACCOUNT_ACTION = "GetAccount";

    private static final GetAccountRequestMarshaller instance = new GetAccountRequestMarshaller();

    public static GetAccountRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Binds protocolMarshaller with getAccountRequestDTO parameters to marshall.
     *
     * @param getAccountRequestDTO         the request dto of type GetAccountRequestDTO
     * @param protocolMarshaller the protocol marshaller
     */
    @Override
    public void marshall(GetAccountRequestDTO getAccountRequestDTO, ProtocolRequestMarshaller<GetAccountRequestDTO> protocolMarshaller) {

        super.marshall(getAccountRequestDTO, protocolMarshaller, GET_ACCOUNT_ACTION);

        int marshallCount = 0;

        try {
            if (StringUtils.isNullOrEmpty(getAccountRequestDTO.getAccountName())
                    && StringUtils.isNullOrEmpty(getAccountRequestDTO.getAccountId())
                    && StringUtils.isNullOrEmpty(getAccountRequestDTO.getAccountArn())
                    && StringUtils.isNullOrEmpty(getAccountRequestDTO.getCanonicalId())
                    && StringUtils.isNullOrEmpty(getAccountRequestDTO.getEmailAddress()))
             {
                throw new VaultClientException("One of account-name, account-id, email, account-arn or canonical-id needs to be specified");
            }

            if (!StringUtils.isNullOrEmpty(getAccountRequestDTO.getAccountName())) {
                protocolMarshaller.marshall(getAccountRequestDTO.getAccountName(), ACCOUNT_NAME_BINDING);
                marshallCount++;
            }

            if (!StringUtils.isNullOrEmpty(getAccountRequestDTO.getAccountId())) {
                protocolMarshaller.marshall(getAccountRequestDTO.getAccountId(), ACCOUNT_ID_BINDING);
                marshallCount++;
            }

            if (!StringUtils.isNullOrEmpty(getAccountRequestDTO.getAccountArn())) {
                protocolMarshaller.marshall(getAccountRequestDTO.getAccountArn(), ACCOUNT_ARN_BINDING);
                marshallCount++;
            }

            if (!StringUtils.isNullOrEmpty(getAccountRequestDTO.getCanonicalId())) {
                protocolMarshaller.marshall(getAccountRequestDTO.getCanonicalId(), CANONICAL_ID_BINDING);
                marshallCount++;
            }

            if (!StringUtils.isNullOrEmpty(getAccountRequestDTO.getEmailAddress())) {
                protocolMarshaller.marshall(getAccountRequestDTO.getEmailAddress(), EMAIL_ADDRESS_BINDING);
                marshallCount++;
            }

            if(marshallCount > MAX_NUMBER_OF_REQUEST_PARAMETERS){
                throw new VaultClientException("arn, name, id, emailAddress and canonicalId IDs are exclusive in the request");
            }


        } catch (Exception e) {
            throw new VaultClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }
}
