package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Represents a response to a request to generate an access key for an account in the Vault system.
 *
 * A `GenerateAccountAccessKeyResponse` object contains the following data for an account:
 *
 * - `data` - The account secret key data.
 *  @see AccountSecretKeyData
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 * by application code.
 */
@Data
public class GenerateAccountAccessKeyResponse implements Serializable {
    private static final long serialVersionUID = 620924237899261417L;

    /**
     * The account secret key data.
     * @see AccountSecretKeyData
     */
    AccountSecretKeyData data;
}
