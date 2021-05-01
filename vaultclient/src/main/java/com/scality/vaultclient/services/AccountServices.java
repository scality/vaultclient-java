package com.scality.vaultclient.services;

import com.amazonaws.Response;
import com.scality.vaultclient.dto.*;

public interface AccountServices {
    /**
     * Create an account.
     *
     * @param createAccountRequestDTO the create account request dto
     * @return the Create Account response
     */
    Response<CreateAccountResponseDTO> createAccount(CreateAccountRequestDTO createAccountRequestDTO);

    /**
     * List accounts.
     *
     * @param listAccountsRequestDTO the list accounts request dto
     * @return the List Accounts response
     */
    Response<ListAccountsResponseDTO> listAccounts(ListAccountsRequestDTO listAccountsRequestDTO);

    /**
     * Get account.
     *
     * @param getAccountRequestDTO the get account request dto
     * @return the Get Account response
     */
    Response<AccountData> getAccount(GetAccountRequestDTO getAccountRequestDTO);

    /**
     * List accounts.
     *
     * @param generateAccountAccessKeyRequest the generate account access key request dto
     * @return the Generate Account Access Key response
     */
    Response<GenerateAccountAccessKeyResponse> generateAccountAccessKey(GenerateAccountAccessKeyRequest generateAccountAccessKeyRequest);
}
