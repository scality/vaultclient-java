package com.scality.vaultclient.services;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Response;
import com.amazonaws.client.AwsSyncClientParams;
import com.amazonaws.metrics.RequestMetricCollector;
import com.scality.vaultclient.dto.CreateAccountRequestDTO;
import com.scality.vaultclient.dto.CreateAccountResponseDTO;

public class AccountServicesClient extends AmazonWebServiceClient implements AccountServices {

    /**
     * Constructs a new AmazonWebServiceClient object using the specified
     * configuration.
     *
     * @param clientConfiguration The client configuration for this client.
     */
    public AccountServicesClient(ClientConfiguration clientConfiguration) {
        super(clientConfiguration);
    }

    /**
     * Constructs a new AmazonWebServiceClient object using the specified
     * configuration and request metric collector.
     *
     * @param clientConfiguration    The client configuration for this client.
     * @param requestMetricCollector optional request metric collector to be used at the http
     */
    public AccountServicesClient(ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        super(clientConfiguration, requestMetricCollector);
    }

    protected AccountServicesClient(ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector, boolean disableStrictHostNameVerification) {
        super(clientConfiguration, requestMetricCollector, disableStrictHostNameVerification);
    }

    protected AccountServicesClient(AwsSyncClientParams clientParams) {
        super(clientParams);
    }

    @Override
    public Response<CreateAccountResponseDTO> createAccount(CreateAccountRequestDTO createAccountRequestDTO) {
        return null;
    }

}
