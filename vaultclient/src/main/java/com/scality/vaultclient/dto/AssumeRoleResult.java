package com.scality.vaultclient.dto;

import com.amazonaws.services.securitytoken.model.Credentials;
import lombok.Data;

import java.io.Serializable;

@Data
public class AssumeRoleResult implements Serializable {
    private static final long serialVersionUID = 7612065953665086104L;
    Credentials Credentials;
    String AssumedRoleUser;
}
