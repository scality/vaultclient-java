package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Represents an account in the Vault system.
 *
 * An account is a data transfer object (DTO) that contains information about an account, including
 * its name, email address, unique identifier, quota, and creation date. The `data` field is an
 * instance of the `AccountData` class, which contains the account's data in a structured format.
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 * by application code.
 */
@Data
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *  The account's data.
     *  @see AccountData
     */
    AccountData data;
}
