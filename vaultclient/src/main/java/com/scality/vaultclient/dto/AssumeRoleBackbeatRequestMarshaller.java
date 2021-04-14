package com.scality.vaultclient.dto;

import com.amazonaws.protocol.MarshallLocation;
import com.amazonaws.protocol.MarshallingInfo;
import com.amazonaws.protocol.MarshallingType;
import com.amazonaws.protocol.ProtocolRequestMarshaller;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.util.StringUtils;
import com.scality.vaultclient.services.VaultClientException;

/**
 * The type AssumeRoleBackbeatRequestMarshaller extends GenericRequestMarshaller to marshall AssumeRoleRequest for AssumeRoleBackbeat API.
 */
public class AssumeRoleBackbeatRequestMarshaller extends GenericRequestMarshaller<AssumeRoleRequest>{

    private static final MarshallingInfo<String> ROLE_ARN_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("RoleArn").build();
    private static final MarshallingInfo<String> ROLE_SESSION_NAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("RoleSessionName").build();
    private static final MarshallingInfo<String> POLICY_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("Policy").build();
    private static final MarshallingInfo<String> DURATION_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("DurationSeconds").build();
    private static final MarshallingInfo<String> EXTERNAL_ID_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("ExternalId").build();
    private static final MarshallingInfo<String> SERIAL_NUMBER_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("SerialNumber").build();
    private static final MarshallingInfo<String> TOKEN_CODE_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
            .marshallLocationName("TokenCode").build();


    private static final String ASSUME_ROLE_BACKBEAT_ACTION = "AssumeRoleBackbeat";

    private static final AssumeRoleBackbeatRequestMarshaller instance = new AssumeRoleBackbeatRequestMarshaller();

    public static AssumeRoleBackbeatRequestMarshaller getInstance() {
        return instance;
    }

    /**
     * Binds protocolMarshaller with assumeRoleRequest parameters to marshall.
     *
     * @param assumeRoleRequest         the request dto of type AssumeRoleRequest
     * @param protocolMarshaller the protocol marshaller
     */
    @Override
    public void marshall(AssumeRoleRequest assumeRoleRequest, ProtocolRequestMarshaller<AssumeRoleRequest> protocolMarshaller) {

        super.marshall(assumeRoleRequest, protocolMarshaller, ASSUME_ROLE_BACKBEAT_ACTION);

        try {
            if (StringUtils.isNullOrEmpty(assumeRoleRequest.getRoleArn())) {
                    throw new VaultClientException("invalid role arn.");
            }

            if (StringUtils.isNullOrEmpty(assumeRoleRequest.getRoleSessionName())) {
                throw new VaultClientException("invalid role session name.");
            }

            protocolMarshaller.marshall(StringUtils.fromString(assumeRoleRequest.getRoleArn()), ROLE_ARN_BINDING);
            protocolMarshaller.marshall(StringUtils.fromString(assumeRoleRequest.getRoleSessionName()), ROLE_SESSION_NAME_BINDING);

            if (assumeRoleRequest.getPolicy() != null) {
                protocolMarshaller.marshall(StringUtils.fromString(assumeRoleRequest.getPolicy()), POLICY_BINDING);
            }

            if (assumeRoleRequest.getDurationSeconds() != null) {
                protocolMarshaller.marshall(StringUtils.fromInteger(assumeRoleRequest.getDurationSeconds()), DURATION_BINDING);
            }

            if (assumeRoleRequest.getExternalId() != null) {
                protocolMarshaller.marshall(StringUtils.fromString(assumeRoleRequest.getExternalId()), EXTERNAL_ID_BINDING);
            }

            if (assumeRoleRequest.getSerialNumber() != null) {
                protocolMarshaller.marshall(StringUtils.fromString(assumeRoleRequest.getSerialNumber()), SERIAL_NUMBER_BINDING);
            }

            if (assumeRoleRequest.getTokenCode() != null) {
                protocolMarshaller.marshall(StringUtils.fromString(assumeRoleRequest.getTokenCode()), TOKEN_CODE_BINDING);
            }

        } catch (Exception e) {
            throw new VaultClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
        }
    }
}
