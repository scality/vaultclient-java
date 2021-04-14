package com.scality.vaultclient.services;

import com.amazonaws.Response;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.scality.vaultclient.dto.*;

/**
 * Account Services Java client.
 * <p>
 * Responsible for all Vault Account Service API invocations.
 */
public class SecurityTokenServicesClient extends BaseServicesClient implements SecurityTokenServices {

    /**
     * Assume Role of any Account using Super Admin Credentials.
     *
     * @param assumeRoleRequest the assumeRole request dto
     * @return the AssumeRoleBackbeat response
     */
    @Override
    public Response<AssumeRoleResult> assumeRoleBackbeat(AssumeRoleRequest assumeRoleRequest) {
        return execute(assumeRoleRequest,
                "AssumeRoleBackbeat",
                AssumeRoleBackbeatRequestMarshaller.getInstance(),
                new GenericAWSResponseJsonUnmarshaller<AssumeRoleResult>(AssumeRoleResult.class));
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
    public SecurityTokenServicesClient(AWSCredentials awsCredentials) {
        super(awsCredentials);
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
    public SecurityTokenServicesClient(AmazonHttpClient client) {
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
    public SecurityTokenServicesClient(AmazonHttpClient client, AWSCredentials awsCredentials) {
        super(client, awsCredentials);
    }
}
