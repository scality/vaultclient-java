package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Represents the data for an account in the Vault system.
 *
 * An `AccountData` object contains the following data for an account:
 *
 * - `arn` - The Amazon Resource Name (ARN) for the account.
 * - `name` - The name of the account.
 * - `emailAddress` - The email address associated with the account.
 * - `id` - The unique identifier for the account.
 * - `quotaMax` - The maximum storage quota for the account in bytes.
 * - `quota` - The current storage quota usage for the account in bytes.
 * - `createDate` - The date when the account was created.
 * - `canonicalId` - The canonical ID for the account.
 * - `aliasList` - A list of alias names associated with the account.
 * - `oidcpList` - A list of OpenID Connect Provider (OIDCP) endpoint URLs associated with the account.
 * - `customAttributes` - A map of custom attributes associated with the account.
 */
@Data
public class AccountData implements Serializable {
    private static final long serialVersionUID = 1L;

    /** The Amazon Resource Name (ARN) for the account. */
    String arn;

    /** The name of the account. */
    String name;

    /** The email address associated with the account. */
    String emailAddress;

    /** The unique identifier for the account. */
    String id;

    /** The maximum storage quota for the account in bytes. */
    long quotaMax;

    /** The current storage quota usage for the account in bytes. */
    long quota; // This will show up only in list accounts response

    /** The date when the account was created. */
    Date createDate;

    /** The canonical ID for the account. */
    String canonicalId;

    /** A list of alias names associated with the account. */
    List<String> aliasList;

    /** A list of OpenID Connect Provider (OIDCP) endpoint URLs associated with the account. */
    List<String> oidcpList;

    /** A map of custom attributes associated with the account. */
    Map<String,String> customAttributes;
}
