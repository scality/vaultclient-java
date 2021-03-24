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
public class ListAccountsRequestDTO extends com.amazonaws.AmazonWebServiceRequest implements Serializable {

    private static final long serialVersionUID = 7894667787877184061L;

    private String marker;

    private int maxItems;

    private String filterKey;

    private String filterKeyStartsWith;


}
