package com.scality.vaultclient.dto;

import com.google.gson.Gson;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.IOUtils;
import com.scality.vaultclient.services.VaultClientException;

public class CreateAccountResponseJsonUnmarshaller implements Unmarshaller<CreateAccountResponseDTO, JsonUnmarshallerContext> {

    @Override
    public CreateAccountResponseDTO unmarshall(JsonUnmarshallerContext context) throws Exception {
        try {
            if (context.getHttpResponse().getContent() != null) {
                String response = IOUtils.toString(context.getHttpResponse().getContent());
                return new Gson().fromJson(response, CreateAccountResponseDTO.class);
            }
        } catch (Exception e) {
            throw new VaultClientException("Unable to unmarshall response content", e);
        }
        return null;
    }

    private static CreateAccountResponseJsonUnmarshaller instance;

    public static CreateAccountResponseJsonUnmarshaller getInstance() {
        if (instance == null)
            instance = new CreateAccountResponseJsonUnmarshaller();
        return instance;
    }
}