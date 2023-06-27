package com.scality.vaultclient.dto;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static com.scality.vaultclient.utils.VaultServicesTestConstants.*;

//@SuppressWarnings("ConstantConditions")
class CreateAccountRequestDTOTest {

    private static final String DEFAULT_ACCOUNT_ID = "001583654825";

    private static final String DEFAULT_ACCOUNT_NAME = "Account5425";

    private static final String DEFAULT_EMAIL_ADDR = "xyz@scality.com";

    @Test
    void createAccountRequestValidation() throws Exception {

        String email_address = DEFAULT_EMAIL_ADDR;
        String name = DEFAULT_ACCOUNT_NAME;

        Map<String, String> customAttributes  = new HashMap<>() ;
        customAttributes.put("cd_tenant_id%3D%3Dccf2139b-7435-426b-b4d2-b917392d9540", "");
        customAttributes.put("cd_tenant_id%3D%3D27598f0b-3696-4d04-81f3-76f89b032d7d", "");

        CreateAccountRequestDTO createAccountRequestDTO = CreateAccountRequestDTO.builder()
                .emailAddress(email_address)
                .name(name)
                .quotaMax(DEFAULT_QUOTA_MAX)
                .externalAccountId(DEFAULT_ACCOUNT_ID)
                .customAttributes(customAttributes)
                .build();


        assertEquals(email_address, createAccountRequestDTO.getEmailAddress(),"Invalid Email address");
        assertEquals(name, createAccountRequestDTO.getName(), "Invalid Name");
        assertEquals(DEFAULT_QUOTA_MAX, createAccountRequestDTO.getQuotaMax(), "Invalid quota" );
        assertEquals(DEFAULT_ACCOUNT_ID, createAccountRequestDTO.getExternalAccountId(), "Invalid ExternalAccountId");
        assertEquals(customAttributes, createAccountRequestDTO.getCustomAttributes(), "Invalid Custom Attributes Map");
        assertNotNull(createAccountRequestDTO.toString());
    }

    @Test
    void createAccountRequestWithNullEmail(){

        String email_address = DEFAULT_EMAIL_ADDR;
        String name = null;

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO.builder()
                    .emailAddress(email_address)
                    .name(name)
                    .quotaMax(DEFAULT_QUOTA_MAX)
                    .build();
        }, "Expected a NullPointerException");
    }

    @Test
    void createAccountRequestWithNullName(){

        String email_address = null;
        String name = DEFAULT_ACCOUNT_NAME;

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO requestDTO = new CreateAccountRequestDTO();
            requestDTO.setName(name);
            requestDTO.setExternalAccountId("");
            requestDTO.setQuotaMax(DEFAULT_QUOTA_MAX);
            requestDTO.setEmailAddress(email_address);
        }, "Expected a NullPointerException");
    }
}
