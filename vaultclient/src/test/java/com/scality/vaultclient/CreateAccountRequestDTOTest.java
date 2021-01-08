package com.scality.vaultclient;

import com.scality.vaultclient.dto.CreateAccountRequestDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@SuppressWarnings("ConstantConditions")
public class CreateAccountRequestDTOTest {

    private static final String DEFAULT_ACCOUNT_ID = "001583654825";

    private static final String DEFAULT_ACCOUNT_NAME = "Account5425";

    private static final String DEFAULT_EMAIL_ADDR = "xyz@scality.com";

    @Test
    public void createAccountRequestValidation() throws Exception {

        String email_address = DEFAULT_EMAIL_ADDR;
        String name = DEFAULT_ACCOUNT_NAME;

        CreateAccountRequestDTO createAccountRequestDTO = CreateAccountRequestDTO.builder()
                .emailAddress(email_address)
                .name(name)
                .quotaMax(10)
                .externalAccountId(DEFAULT_ACCOUNT_ID)
                .build();


        assertEquals(email_address, createAccountRequestDTO.getEmailAddress(),"Invalid Email address");
        assertEquals(name, createAccountRequestDTO.getName(), "Invalid Name");
        assertEquals(10, createAccountRequestDTO.getQuotaMax(), "Invalid quota" );
        assertEquals(DEFAULT_ACCOUNT_ID, createAccountRequestDTO.getExternalAccountId(), "Invalid ExternalAccountId");
    }

    @Test
    public void createAccountRequestWithNullEmail(){

        String email_address = DEFAULT_EMAIL_ADDR;
        String name = null;

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO.builder()
                    .emailAddress(email_address)
                    .name(name)
                    .quotaMax(10)
                    .build();
        }, "Expected a NullPointerException");
    }

    @Test
    public void createAccountRequestWithNullName(){

        String email_address = null;
        String name = DEFAULT_ACCOUNT_NAME;

        assertThrows(NullPointerException.class, () -> {
            CreateAccountRequestDTO.builder()
                    .emailAddress(email_address)
                    .name(name)
                    .quotaMax(10)
                    .build();
        }, "Expected a NullPointerException");
    }
}
