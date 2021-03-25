package com.scality.vaultclient.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VaultClientUtilsTest {

    @Test
    void testIsStringInteger() {
        assertTrue(VaultClientUtils.isStringInteger("1"));
    }

    @Test
    void testIsStringIntegerInvalidValue() {
        assertFalse(VaultClientUtils.isStringInteger("number"));
    }
}
