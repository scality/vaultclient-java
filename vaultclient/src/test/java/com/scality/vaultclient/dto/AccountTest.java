package com.scality.vaultclient.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class AccountTest {

    private Account accountUnderTest;

    private static final AccountData ACCOUNT_DATA_MOCK = mock(AccountData.class);

    @BeforeEach
    void setUp() {
        accountUnderTest = new Account();
        accountUnderTest.data = ACCOUNT_DATA_MOCK;
    }

    @Test
    void testEqualsAndHashCode() {
        // Setup
        Account account = new Account();
        account.data = ACCOUNT_DATA_MOCK;
        // Run the test
        assertEquals(accountUnderTest,account);
        assertEquals(accountUnderTest.hashCode(), account.hashCode());
    }

    @Test
    void testToString() {
        // Setup

        // Run the test
        final String result = accountUnderTest.toString();

        // Verify the results
        assertNotNull( result);
    }

    public Account getAccountUnderTest() {
        return accountUnderTest;
    }

    public void setAccountUnderTest(Account accountUnderTest) {
        this.accountUnderTest = accountUnderTest;
    }
}
