package com.ct.firsthand.importapi;

import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.importapi.client.ProjectApiRoot;
import com.commercetools.importapi.defaultconfig.ImportApiRootBuilder;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;

public class ImportClientBuilder {

    private static final String clientId = System.getenv("IMPORT_API_CT_CLIENT_ID");
    private static final String clientSecret = System.getenv("IMPORT_API_CT_CLIENT_SECRET");
    private static final String projectKey = System.getenv("COMMERCETOOLS_PROJECT_KEY");

    public static ProjectApiRoot createImportApiClient() {

        return ImportApiRootBuilder.of()
                .defaultClient(
                        ClientCredentials.of()
                                .withClientId(clientId)
                                .withClientSecret(clientSecret)
                                //.withScopes("manage_import_containers:handson manage_customer_groups:handson manage_customers:handson")
                                .build(),
                        ServiceRegion.AWS_EU_CENTRAL_1.getOAuthTokenUrl(),
                        "https://import.eu-central-1.aws.commercetools.com/")
                .build(projectKey);
    }

}

