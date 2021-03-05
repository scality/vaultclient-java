package com.scality.vaultclient.services;

import com.amazonaws.Response;
import com.scality.vaultclient.dto.CreateAccountRequestDTO;
import com.scality.vaultclient.dto.CreateAccountResponseDTO;

public interface AccountServices {
    Response<CreateAccountResponseDTO> createAccount(CreateAccountRequestDTO createAccountRequestDTO);
}
