package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Represents a request to get an account in the Vault system.
 *
 * A `GetAccountRequestDTO` object contains the following data for an account:
 *
 * - `accountArn` - The account ARN string.
 * - `accountName` - The account name string.
 * - `accountId` - The account ID string.
 * - `canonicalId` - The account canonical ID string.
 * - `emailAddress` - The account email address string.
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
public class GetAccountRequestDTO extends com.amazonaws.AmazonWebServiceRequest implements Serializable {

    private static final long serialVersionUID = 3339334644503630500L;

    /**
     * Account ARN string.
     */
    private String accountArn;

    /**
     * Account name string.
     */
    private String accountName;

    /**
     * Account ID string.
     */
    private String accountId;

    /**
     * Account canonical ID string.
     */
    private String canonicalId;

    /**
     * Account email address string.
     */
    private String emailAddress;


}
