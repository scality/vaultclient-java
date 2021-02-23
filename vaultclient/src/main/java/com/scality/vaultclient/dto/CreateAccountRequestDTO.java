package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.NonNull;

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
