package com.scality.vaultclient.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetUserByAccessKeyRequestDTO extends com.amazonaws.AmazonWebServiceRequest implements Serializable {
    private static final long serialVersionUID = -2303133786158189623L;

    @NonNull
    private String accessKey;
}
