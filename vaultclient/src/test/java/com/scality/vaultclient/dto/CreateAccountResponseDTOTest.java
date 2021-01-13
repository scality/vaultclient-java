package com.scality.vaultclient.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CreateAccountResponseDTOTest {

    private CreateAccountResponseDTO createAccountResponseDTOUnderTest;

    private static final Account ACCOUNT_MOCK = mock(Account.class);

    @BeforeEach
    void setUp() {
        createAccountResponseDTOUnderTest = new CreateAccountResponseDTO();
        createAccountResponseDTOUnderTest.account = ACCOUNT_MOCK;
    }

    @Test
    void testEqualsAndHashcode() {
        // Setup
        CreateAccountResponseDTO createAccountResponseDTOUnderTest2 = new CreateAccountResponseDTO();
        createAccountResponseDTOUnderTest2.account = ACCOUNT_MOCK;

        // Run the test
        assertEquals(createAccountResponseDTOUnderTest, createAccountResponseDTOUnderTest2);
        assertEquals(createAccountResponseDTOUnderTest.hashCode(), createAccountResponseDTOUnderTest2.hashCode());

    }

    @Test
    void testToString() {
        assertNotNull(createAccountResponseDTOUnderTest.toString());
    }

    public CreateAccountResponseDTO getCreateAccountResponseDTOUnderTest() {
        return createAccountResponseDTOUnderTest;
    }

    public void setCreateAccountResponseDTOUnderTest(CreateAccountResponseDTO createAccountResponseDTOUnderTest) {
        this.createAccountResponseDTOUnderTest = createAccountResponseDTOUnderTest;
    }
}
