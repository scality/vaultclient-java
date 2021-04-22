package com.scality.vaultclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountSecretKeyData implements Serializable {
    private static final long serialVersionUID = 563592591121286043L;

    private String id;

    private String value;

    private String tag;

    private Date createDate;

    private String lastUsedService;

    private String lastUsedRegion;

    private Date lastUsedDate;

    private Date notBefore;

    private Date notAfter;

    private String status;

    private String userId;

}
