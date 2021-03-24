package com.scality.vaultclient.services;

import com.amazonaws.Response;
import com.scality.vaultclient.dto.CreateAccountRequestDTO;
import com.scality.vaultclient.dto.CreateAccountResponseDTO;
import com.scality.vaultclient.dto.ListAccountsRequestDTO;
import com.scality.vaultclient.dto.ListAccountsResponseDTO;

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
}
