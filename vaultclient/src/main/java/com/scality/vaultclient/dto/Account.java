package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    AccountData data;
}
