package com.scality.vaultclient.utils;

/**
 * The helper class all Vault client services and operation.
 */
public final class VaultClientUtils {
    private VaultClientUtils(){}

    /**
     * Check if the string is an integer.
     * @param number given strung number
     * @return boolean
     */
    public static boolean isStringInteger(String number ){
        try{
            Integer.parseInt(number);
        }catch(Exception e ){
            return false;
        }
        return true;
    }
}
