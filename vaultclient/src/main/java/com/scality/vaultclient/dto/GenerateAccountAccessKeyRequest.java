package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Represents a request to generate an account access key in the Vault system.
 *
 * A `GenerateAccountAccessKeyRequest` object contains the following data for an account:
 *
 * - `accountName` - The account name string.
 * - `externalAccessKey` - The external access key string.
 * - `externalSecretKey` - The external secret key string.
 * - `durationSeconds` - The duration in seconds.
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 * by application code.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenerateAccountAccessKeyRequest extends com.amazonaws.AmazonWebServiceRequest implements Serializable {

    private static final long serialVersionUID = -2303133786158189623L;

    /**
     * Account name string.
     */
    @NonNull
    private String accountName;

    /**
     * External access key string.
     */
    private String externalAccessKey;

    /**
     * External secret key string.
     */
    private String externalSecretKey;

    /**
     * Duration in seconds.
     */
    private long durationSeconds;

}
