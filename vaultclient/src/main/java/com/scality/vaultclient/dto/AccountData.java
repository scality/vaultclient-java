package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AccountData implements Serializable {
    private static final long serialVersionUID = 1L;
    String arn;
    String name;
    String emailAddress;
    String id;
    int quotaMax;
    Date createDate;
    String canonicalId;
    List<String> aliasList;
    List<String> oidcpList;
}
