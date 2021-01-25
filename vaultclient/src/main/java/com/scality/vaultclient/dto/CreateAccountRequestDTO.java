package com.scality.vaultclient.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateAccountRequestDTO extends com.amazonaws.AmazonWebServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private String externalAccountId;

    @NonNull
    private String name;

    @NonNull
    private String emailAddress;

    private int quotaMax;


}
