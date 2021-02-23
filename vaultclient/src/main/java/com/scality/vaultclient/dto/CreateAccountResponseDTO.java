package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateAccountResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    Account account;
}
