package com.ct.firsthand.client;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;

import java.io.IOException;

public class DefaultClientBuilder {

    public static ProjectApiRoot projectApiRoot;

    public static ProjectApiRoot createApiClient() throws IOException {

        String clientId = System.getenv("COMMERCETOOLS_CLIENT_ID");
        String clientSecret = System.getenv("COMMERCETOOLS_CLIENT_SECRET");
        String projectKey = System.getenv("COMMERCETOOLS_PROJECT_KEY");

        projectApiRoot = ApiRootBuilder.of()
                .defaultClient(
                        ClientCredentials.of()
                                .withClientId(clientId)
                                .withClientSecret(clientSecret)
                                .build(),
                        ServiceRegion.AWS_EU_CENTRAL_1.getOAuthTokenUrl(),
                        ServiceRegion.AWS_EU_CENTRAL_1.getApiUrl()
                        //"https://auth.eu-central-1.aws.commercetools.com",
                        //"https://api.eu-central-1.aws.commercetools.com"
                )



                .build(projectKey);

        return projectApiRoot;
    }
}
