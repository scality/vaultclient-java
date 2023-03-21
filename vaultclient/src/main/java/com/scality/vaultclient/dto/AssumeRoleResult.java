package com.scality.vaultclient.dto;

import com.amazonaws.services.securitytoken.model.Credentials;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a response to a request to assume a role in the Vault system.
 *
 * A `AssumeRoleResult` object contains the following data for an account:
 *
 * - `Credentials` - The credentials.
 *  @see Credentials
 * - `AssumedRoleUser` - The assumed role user.
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 * by application code.
 */
@Data
public class AssumeRoleResult implements Serializable {
    private static final long serialVersionUID = 7612065953665086104L;

    /**
     * The credentials.
     * @see Credentials
     */
    Credentials Credentials;

    /**
     * The assumed role user.
     */
    String AssumedRoleUser;
}
