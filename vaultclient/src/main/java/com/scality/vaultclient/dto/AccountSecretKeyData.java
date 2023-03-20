package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents the data for an access key ID in the Vault system.
 *
 * A `AccountSecretKeyData` object contains the following data for an access key ID:
 *
 * - `id` - The secret key id.
 * - `value` - The secret key value.
 * - `tag` - The secret key tag.
 * - `createDate` - The secret key create date.
 * - `lastUsedService` - The secret key last used service.
 * - `lastUsedRegion` - The secret key last used region.
 * - `lastUsedDate` - The secret key last used date.
 * - `notBefore` - The date before which secret key cannot be used.
 * - `notAfter` - The date after which secret key cannot be used.
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 * by application code.
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountSecretKeyData implements Serializable {
    private static final long serialVersionUID = 563592591121286043L;

    /**
     * The secret key id.
     */
    private String id;

    /**
     * The secret key value.
     */
    private String value;

    /**
     * The secret key tag.
     */
    private String tag;

    /**
     * The secret key create date.
     */
    private Date createDate;

    /**
     * The secret key last used service.
     */
    private String lastUsedService;

    /**
     * The secret key last used region.
     */
    private String lastUsedRegion;

    /**
     * The secret key last used date.
     */
    private Date lastUsedDate;

    /**
     * The date before which secret key cannot be used.
     */
    private Date notBefore;

    /**
     * The date after which secret key cannot be used.
     */
    private Date notAfter;

    /**
     * The secret key status.
     */
    private String status;

    /**
     * The secret key user id.
     */
    private String userId;

}
