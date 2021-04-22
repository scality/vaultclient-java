package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenerateAccountAccessKeyRequest extends com.amazonaws.AmazonWebServiceRequest implements Serializable {

    private static final long serialVersionUID = -2303133786158189623L;

    @NonNull
    private String accountName;

    private String externalAccessKey;

    private String externalSecretKey;

    private long durationSeconds;

}
