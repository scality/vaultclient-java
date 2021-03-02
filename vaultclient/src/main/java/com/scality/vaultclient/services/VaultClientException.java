package com.scality.vaultclient.services;

import com.amazonaws.AmazonServiceException;

public class VaultClientException extends AmazonServiceException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new VaultClientException with the specified message, and root
     * cause.
     *
     * @param message
     *            An error message describing why this exception was thrown.
     * @param t
     *            The underlying cause of this exception.
     */
    public VaultClientException(String message, Exception t) {
        super(message, t);
    }

    /**
     * Creates a new VaultClientException with the specified message.
     *
     * @param message
     *            An error message describing why this exception was thrown.
     */
    public VaultClientException(String message) {
        super(message);
    }
}