package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a response to a request to list accounts in the Vault system.
 *
 * A `ListAccountsResponseDTO` object contains the following data for an account:
 *
 * - `accounts` - A list of accounts.
 *  @see AccountData
 * - `isTruncated` - Whether the list is truncated.
 * - `marker` - The marker to start listing from.
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 * by application code.
 */
@Data
public class ListAccountsResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * A list of accounts.
     */
    List<AccountData> accounts;

    /**
     * Whether the list is truncated.
     */
    boolean isTruncated;

    /**
     * The marker to start listing from.
     */
    String marker;
}
