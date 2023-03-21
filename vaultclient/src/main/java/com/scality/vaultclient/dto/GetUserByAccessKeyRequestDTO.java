package com.scality.vaultclient.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Represents a request to get a user by access key in the Vault system.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetUserByAccessKeyRequestDTO extends com.amazonaws.AmazonWebServiceRequest implements Serializable {
    private static final long serialVersionUID = -2303133786158189623L;

    /**
     * Access key string.
     */
    @NonNull
    private String accessKey;
}
