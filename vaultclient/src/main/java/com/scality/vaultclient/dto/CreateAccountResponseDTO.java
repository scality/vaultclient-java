package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Represents a response to a request to create an account in the Vault system.
 *
 * A `CreateAccountResponseDTO` object contains the following data for an account:
 *
 * - `account` - The account.
 *   @see Account
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 * by application code.
 */
@Data
public class CreateAccountResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * The account.
     * @see Account
     */
    Account account;
}
