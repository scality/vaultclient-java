package com.scality.vaultclient.services;

import com.amazonaws.Response;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.scality.vaultclient.dto.AssumeRoleResult;

/**
 * Interface for accessing the Vault Security Token Service.
 */
public interface SecurityTokenServices {
    /**
     * Assume Role of any Account using Super Admin Credentials.
     *
     * @param assumeRoleRequest the assumeRole request dto
     * @return the AssumeRoleBackbeat response
     */
    Response<AssumeRoleResult> assumeRoleBackbeat(AssumeRoleRequest assumeRoleRequest);
}
