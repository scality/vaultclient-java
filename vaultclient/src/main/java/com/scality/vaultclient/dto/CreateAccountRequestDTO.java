package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents a request to create an account in the Vault system.
 *
 * A `CreateAccountRequestDTO` object contains the following data for an account:
 *
 * - `externalAccountId` - The external account ID string consisting of 12 digits.
 * - `name` - The account name string.
 * - `emailAddress` - The account email address string.
 * - `quotaMax` - The account quota in bytes.
 * - `customAttributes` - The account custom attributes Map.
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
public class CreateAccountRequestDTO extends com.amazonaws.AmazonWebServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Account ID string consisting of 12 digits.
     */
    private String externalAccountId;

    /**
     * Account name string.
     */
    @NonNull
    private String name;

    /**
     * Account email address string.
     */
    @NonNull
    private String emailAddress;

    /**
     * Account quota in bytes.
     */
    private int quotaMax;

    /**
     * Account custom attributes Map.
     */
    private Map<String, String> customAttributes;
//    private String customAttributes;


}
