package com.scality.vaultclient.dto;

import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.scality.vaultclient.services.VaultClientException;

/**
 * The type Create account request marshaller extends GenericRequestMarshaller to marshall Create Account Requests.
 */
public class CreateAccountRequestMarshaller extends GenericRequestMarshaller<CreateAccountRequestDTO>{

    private static final MarshallingInfo<String> NAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("name").build();
    private static final MarshallingInfo<String> EMAILADDRESS_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("emailAddress").build();
    private static final MarshallingInfo<Long> QUOTAMAX_BINDING = MarshallingInfo.builder(MarshallingType.LONG)
            .marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("quotaMax").build();

    private static final MarshallingInfo<String> EXTERNALACCOUNTID_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("externalAccountId").build();
    private static final MarshallingInfo<String> CUSTOM_ATTRIBUTES_BINDING = MarshallingInfo.builder(MarshallingType.JSON_VALUE).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("customAttributes").build();
    private static final String CREATE_ACCOUNT_ACTION = "CreateAccount";

    private static final CreateAccountRequestMarshaller instance = new CreateAccountRequestMarshaller();

    /**
     * static factory method that returns a singleton instance of the CreateAccountRequestMarshaller class.
     *
     * @return instance of CreateAccountRequestMarshaller
     */
    public static CreateAccountRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Binds protocolMarshaller with createAccountRequestDTO parameters to marshall.
     *
     * @param createAccountRequestDTO         the request dto of type CreateAccountRequestDTO
     * @param protocolMarshaller the protocol marshaller
     */
    @Override
    public void marshall(CreateAccountRequestDTO createAccountRequestDTO, ProtocolRequestMarshaller<CreateAccountRequestDTO> protocolMarshaller) {

        super.marshall(createAccountRequestDTO, protocolMarshaller, CREATE_ACCOUNT_ACTION);

        if (StringUtils.isNullOrEmpty(createAccountRequestDTO.getName())) {
            throw new VaultClientException("accountName is required.");
        }

        if (StringUtils.isNullOrEmpty(createAccountRequestDTO.getEmailAddress())) {
            throw new VaultClientException("email is required.");
        }

        try {
            protocolMarshaller.marshall(createAccountRequestDTO.getName(), NAME_BINDING);
            protocolMarshaller.marshall(createAccountRequestDTO.getEmailAddress(), EMAILADDRESS_BINDING);

            if (createAccountRequestDTO.getQuotaMax() < 0) {
                throw new VaultClientException("Quota must be a non-negative number.");
            } else if (createAccountRequestDTO.getQuotaMax() > 0) {
                protocolMarshaller.marshall(createAccountRequestDTO.getQuotaMax(), QUOTAMAX_BINDING);
            }

            if (createAccountRequestDTO.getExternalAccountId()!=null) {
                if (StringUtils.isNullOrEmpty(createAccountRequestDTO.getExternalAccountId())) {
                    throw new VaultClientException("invalid account id supplied.");
                }
                protocolMarshaller.marshall(createAccountRequestDTO.getExternalAccountId(), EXTERNALACCOUNTID_BINDING);
            }

            if (createAccountRequestDTO.getCustomAttributes()!=null
                    && !createAccountRequestDTO.getCustomAttributes().isEmpty()) {
                protocolMarshaller.marshall(new Gson().toJson(createAccountRequestDTO.getCustomAttributes()), CUSTOM_ATTRIBUTES_BINDING);
            }
        } catch (Exception e) {
            throw new VaultClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }
}
