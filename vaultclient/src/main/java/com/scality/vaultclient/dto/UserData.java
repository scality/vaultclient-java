package com.scality.vaultclient.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserData implements Serializable {
    private static final long serialVersionUID = 1L;
    String arn;
    String name;
    String emailAddress;
    String id;
    Date createDate;
    String parentId;
}
