package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AccountData implements Serializable {
    private static final long serialVersionUID = 1L;
    String arn;
    String name;
    String emailAddress;
    String id;
    int quotaMax;
    int quota; // This will show up only in list accounts response
    Date createDate;
    String canonicalId;
    List<String> aliasList;
    List<String> oidcpList;
    Map<String,String> customAttributes;
}
