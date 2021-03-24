package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListAccountsResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    List<AccountData> accounts;

    boolean isTruncated;

    String marker;
}
