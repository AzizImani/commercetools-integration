package com.ct.firsthand.restapi;

import com.commercetools.api.client.ProjectApiRoot;
import com.ct.firsthand.client.DefaultClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class CustomerFetch {

    private static final String customerId = "4e085d3c-dea3-4f53-9c06-38e7e71d9220";
    private static final Logger logger = LoggerFactory.getLogger(CustomerFetch.class.getName());

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        final ProjectApiRoot client = DefaultClientBuilder.createApiClient();
        String lastName = client.customers()
                .withId(customerId)
                .get()
                .execute()
                .toCompletableFuture().get()
                .getBody()
                .getLastName();
        logger.info("Fetching the customers last name {}", lastName);

        client.close();
    }
}
