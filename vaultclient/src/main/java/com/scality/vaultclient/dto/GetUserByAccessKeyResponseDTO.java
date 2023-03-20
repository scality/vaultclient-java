package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Represents a response to a request to get a user by access key in the Vault system.
 */
@Data
public class GetUserByAccessKeyResponseDTO implements Serializable {
    private static final long serialVersionUID = 620924237899261417L;

    /**
     * The user.
     * @see UserData
     */
    UserData data;
}
