package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Represents a request to list accounts in the Vault system.
 *
 * A `ListAccountsRequestDTO` object contains the following data for an account:
 *
 * - `marker` - The marker to start listing from.
 * - `maxItems` - The maximum number of items to return.
 * - `filterKey` - The filter key.
 * - `filterKeyStartsWith` - The filter key starting pattern.
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
public class ListAccountsRequestDTO extends com.amazonaws.AmazonWebServiceRequest implements Serializable {

    private static final long serialVersionUID = 7894667787877184061L;

    /**
     * The marker to start listing from.
     */
    private String marker;

    /**
     * The maximum number of items to return.
     */
    private int maxItems;

    /**
     * The filter key.
     */
    private String filterKey;

    /**
     * The filter key starting pattern.
     */
    private String filterKeyStartsWith;


}
