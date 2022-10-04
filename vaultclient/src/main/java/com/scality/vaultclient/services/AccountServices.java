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
     * Update an account.
     *
     * @param updateAccountAttributesRequestDTO the update account attributes request dto
     * @return the Create Account response
     */
    Response<AccountData> updateAccountAttributes(UpdateAccountAttributesRequestDTO updateAccountAttributesRequestDTO);

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
     * generate account access key.
     *
     * @param generateAccountAccessKeyRequest the generate account access key request dto
     * @return the Generate Account Access Key response
     */
    Response<GenerateAccountAccessKeyResponse> generateAccountAccessKey(GenerateAccountAccessKeyRequest generateAccountAccessKeyRequest);

    /**
     * Get User By accessKey.
     *
     * @param getUserByAccessKeyRequestDTO the Get User by AccessKey request dto
     * @return the Get User by AccessKey response
     */
    Response<GetUserByAccessKeyResponseDTO> getUserByAccessKey(GetUserByAccessKeyRequestDTO getUserByAccessKeyRequestDTO);

}
