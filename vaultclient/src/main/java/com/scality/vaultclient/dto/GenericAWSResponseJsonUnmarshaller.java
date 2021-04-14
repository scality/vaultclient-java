package com.scality.vaultclient.dto;

import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.IOUtils;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.scality.vaultclient.services.VaultClientException;

import java.util.Date;

/**
 * A GenericAWSResponseJsonUnmarshaller that implements JsonUnmarshaller to unmarshall AWS compliant response object from JSON String
 * extracted from JsonUnmarshallerContext
 */
public class GenericAWSResponseJsonUnmarshaller<T> implements Unmarshaller<T, JsonUnmarshallerContext> {
    final Class<T> typeParameterClass;

    public GenericAWSResponseJsonUnmarshaller(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    /**
     * unmarshall response object from JSON String extracted from JsonUnmarshallerContext
     *
     * @param context the JsonUnmarshallerContext with JSON response string
     * @return result unmarshalled response object
     */
    @Override
    public T unmarshall(JsonUnmarshallerContext context) {
        T result = null;
        try {
            if (context.getHttpResponse().getContent() != null) {
                String response = IOUtils.toString(context.getHttpResponse().getContent());
                Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class,
                        (JsonDeserializer) (json, typeOfT, jsonDeserializationContext) -> new Date(json.getAsLong()))
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
                result = gson.fromJson(response, typeParameterClass);
            }
        } catch (Exception e) {
            throw new VaultClientException("Unable to unmarshall response content", e);
        }
        return result;
    }
}
