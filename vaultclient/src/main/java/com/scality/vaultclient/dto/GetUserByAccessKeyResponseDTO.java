package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetUserByAccessKeyResponseDTO implements Serializable {
    private static final long serialVersionUID = 620924237899261417L;
    UserData data;
}
