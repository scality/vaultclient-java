package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents a request to update an account in the Vault system.
 *
 * A `UpdateAccountAttributesRequestDTO` object contains the following data for an account:
 *
 * - `name` - The account name string.
 * - `customAttributes` - The account custom attributes Map.
 *
 * This class is used primarily for communicating with the Vault API and should not be used directly
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateAccountAttributesRequestDTO extends com.amazonaws.AmazonWebServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Account name string.
     */
    @NonNull
    private String name;

    /**
     * Account custom attributes Map.
     */
    private Map<String, String> customAttributes;


}
