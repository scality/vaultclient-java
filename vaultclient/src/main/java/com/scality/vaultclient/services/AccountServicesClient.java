package com.scality.vaultclient.services;

import com.amazonaws.Response;
import com.scality.vaultclient.dto.CreateAccountRequestDTO;
import com.scality.vaultclient.dto.CreateAccountResponseDTO;

public class AccountServicesClient implements AccountServices {

    @Override
    public Response<CreateAccountResponseDTO> createAccount(CreateAccountRequestDTO createAccountRequestDTO) {
        return null;
    }

}
