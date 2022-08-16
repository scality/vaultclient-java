package com.scality.vaultclient.services;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Response;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.AwsSyncClientParams;
import com.amazonaws.http.AmazonHttpClient;
import com.scality.vaultclient.dto.*;

/**
 * Account Services Java client.
 * <p>
 * Responsible for all Vault Account Service API invocations.
 */
public class AccountServicesClient extends BaseServicesClient implements AccountServices {

    @Override
    public Response<CreateAccountResponseDTO> createAccount(CreateAccountRequestDTO createAccountRequestDTO) {

        return execute(createAccountRequestDTO, "CreateAccount", CreateAccountRequestMarshaller.getInstance(), CreateAccountResponseDTO.class );
    }

    @Override
    public Response<CreateAccountResponseDTO> updateAccountAttributes(UpdateAccountAttributesRequestDTO updateAccountAttributesRequestDTO) {

        return execute(updateAccountAttributesRequestDTO, "UpdateAccountAttributes", UpdateAccountAttributesRequestMarshaller.getInstance(), CreateAccountResponseDTO.class );
    }

    @Override
    public Response<ListAccountsResponseDTO> listAccounts(ListAccountsRequestDTO listAccountsRequestDTO) {

        return execute(listAccountsRequestDTO, "ListAccounts", ListAccountsRequestMarshaller.getInstance(), ListAccountsResponseDTO.class );
    }

    @Override
    public Response<AccountData> getAccount(GetAccountRequestDTO getAccountRequestDTO) {

        return execute(getAccountRequestDTO, "GetAccount",
                GetAccountRequestMarshaller.getInstance(), AccountData.class );
    }

    @Override
    public Response<GenerateAccountAccessKeyResponse> generateAccountAccessKey(GenerateAccountAccessKeyRequest generateAccountAccessKeyRequest) {

        return execute(generateAccountAccessKeyRequest, "GenerateAccountAccessKey",
                GenerateAccountAccessKeyRequestMarshaller.getInstance(), GenerateAccountAccessKeyResponse.class );
    }

    /**
     * Constructs a new client to invoke service methods on IAM. A credentials provider chain will be used that searches
     * for credentials in this order:
     * <ul>
     * <li>Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_KEY</li>
     * <li>Java System Properties - aws.accessKeyId and aws.secretKey</li>
     * <li>Instance profile credentials delivered through the Amazon EC2 metadata service</li>
     * </ul>
     *
     * <p>
     * All service calls made using this new client object are blocking, and will not return until the service call
     * completes.
     *
     * @param awsCredentials the aws credentials
     */
    public AccountServicesClient(AWSCredentials awsCredentials) {
        super(awsCredentials);
    }

    public AccountServicesClient(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
        super(awsCredentials, clientConfiguration);
    }

    /**
     * Constructs a new client to invoke service methods on IAM using the specified parameters.
     *
     * <p>
     * All service calls made using this new client object are blocking, and will not return until the service call
     * completes.
     *
     * @param clientParams Object providing client parameters.
     */
    public AccountServicesClient(AwsSyncClientParams clientParams) {
        super(clientParams);
    }

    /**
     * Constructs a new client to invoke service methods on IAM. A credentials provider chain will be used that searches
     * for credentials in this order:
     * <ul>
     * <li>Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_KEY</li>
     * <li>Java System Properties - aws.accessKeyId and aws.secretKey</li>
     * <li>Instance profile credentials delivered through the Amazon EC2 metadata service</li>
     * </ul>
     *
     * <p>
     * All service calls made using this new client object are blocking, and will not return until the service call
     * completes.
     *
     * @param client *        client object.
     * @see DefaultAWSCredentialsProviderChain
     */
    public AccountServicesClient(AmazonHttpClient client) {
        super(client);
    }

    /**
     * Constructs a new client to invoke service methods on IAM. A credentials provider chain will be used that searches
     * for credentials in this order:
     * <ul>
     * <li>Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_KEY</li>
     * <li>Java System Properties - aws.accessKeyId and aws.secretKey</li>
     * <li>Instance profile credentials delivered through the Amazon EC2 metadata service</li>
     * </ul>
     *
     * <p>
     * All service calls made using this new client object are blocking, and will not return until the service call
     * completes.
     *  @param client         *        client object.
     *
     * @param awsCredentials *        the aws credentials
     */
    public AccountServicesClient(AmazonHttpClient client, AWSCredentials awsCredentials) {
        super(client, awsCredentials);
    }

    @Override
    public Response<GetUserByAccessKeyResponseDTO> getUserByAccessKey(GetUserByAccessKeyRequestDTO getUserByAccessKeyRequestDTO) {
        return execute(getUserByAccessKeyRequestDTO, "GetUserByAccessKey", GetUserByAccessKeyRequestMarshaller.getInstance(), GetUserByAccessKeyResponseDTO.class);
    }
}
