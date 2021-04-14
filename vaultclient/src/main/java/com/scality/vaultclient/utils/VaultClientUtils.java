package com.scality.vaultclient.utils;

/**
 * The helper class all Vault client services and operation.
 */
public final class VaultClientUtils {
    private VaultClientUtils(){}

    public static boolean isStringInteger(String number ){
        try{
            Integer.parseInt(number);
        }catch(Exception e ){
            return false;
        }
        return true;
    }
}
