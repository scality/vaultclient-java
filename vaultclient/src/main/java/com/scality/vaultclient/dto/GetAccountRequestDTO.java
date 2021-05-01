package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetAccountRequestDTO extends com.amazonaws.AmazonWebServiceRequest implements Serializable {

    private static final long serialVersionUID = 3339334644503630500L;

    private String accountArn;

    private String accountName;

    private String accountId;

    private String canonicalId;

    private String emailAddress;


}
