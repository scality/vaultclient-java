package com.scality;

import com.scality.vaultclient.TestApp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestAppTest {

    @Test
    public void testAppHasAGreeting() {
        TestApp classUnderTest = new TestApp();
        assertEquals("Hello world.", classUnderTest.getGreeting());
    }
}
