package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an IAM user in the Vault system.
 *
 * A user is a data transfer object (DTO) that contains information about a user, including its
 * name, email address, unique identifier, and creation date. The `data` field is an instance of the
 * `UserData` class, which contains the user's data in a structured format.
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 * by application code.
 */
@Data
public class UserData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The IAM user's data.
     */
    String arn;

    /**
     * The IAM user's name.
     */
    String name;

    /**
     * The IAM user's email address.
     */
    String emailAddress;

    /**
     * The IAM user's unique identifier.
     */
    String id;

    /**
     * The IAM user's creation date.
     */
    Date createDate;

    /**
     * The IAM user's parent id (account id).
     */
    String parentId;
}
