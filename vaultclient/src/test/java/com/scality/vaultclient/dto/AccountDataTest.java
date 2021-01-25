package com.scality.vaultclient.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class AccountDataTest {

    private AccountData accountDataUnderTest;

    private static final Date DATE = new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime();

    @BeforeEach
    public void setUp() {
        accountDataUnderTest = new AccountData();
        accountDataUnderTest.arn = "arn";
        accountDataUnderTest.name = "name";
        accountDataUnderTest.emailAddress = "emailAddress";
        accountDataUnderTest.id = "id";
        accountDataUnderTest.quotaMax = 0;
        accountDataUnderTest.createDate = DATE;
        accountDataUnderTest.canonicalId = "canonicalId";
    }

    @Test
    void testEqualsAndHashCode() {
        // Setup
        AccountData data = new AccountData();
        data.setEmailAddress("emailAddress");
        data.setName("name");
        data.setArn("arn");
        data.setCreateDate(DATE);
        data.setId("id");
        data.setCanonicalId("canonicalId");
        data.setQuotaMax(0);
        // Run the test
        assertEquals(accountDataUnderTest,data);
        assertEquals(accountDataUnderTest.hashCode(), data.hashCode());
    }

    @Test
    void testToString() {
        // Setup

        // Run the test
        final String result = accountDataUnderTest.toString();

        // Verify the results
        assertNotNull( result);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testAliasList() {
        accountDataUnderTest.aliasList = mock(List.class);
        assertNotNull(accountDataUnderTest.getAliasList());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testOidcpList() {
        accountDataUnderTest.oidcpList = mock(List.class);
        assertNotNull(accountDataUnderTest.getOidcpList());
    }

    public AccountData getAccountDataUnderTest() {
        return accountDataUnderTest;
    }

    public void setAccountDataUnderTest(AccountData accountDataUnderTest) {
        this.accountDataUnderTest = accountDataUnderTest;
    }
}
