package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Represents a user in the Vault system.
 *
 * A user is a data transfer object (DTO) that contains information about a user, including
 * its name, email address, unique identifier, and creation date. The `data` field is an
 * instance of the `UserData` class, which contains the user's data in a structured format.
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 * by application code.
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The user's data.
     * @see UserData
     */
    UserData data;
}
