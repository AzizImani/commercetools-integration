package com.ct.firsthand.restapi;

import com.commercetools.api.client.ProjectApiRoot;
import com.ct.firsthand.client.DefaultClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class CustomerCreate {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final ProjectApiRoot client = DefaultClientBuilder.createApiClient();
        Logger logger = LoggerFactory.getLogger(CustomerCreate.class.getName());
        CustomerService customerService = new CustomerService(client);

        // Create a new customer

        String firstName = customerService.createCustomer(
                        "example-customer@example.com",
                        "password",
                        "john-doe-customer",
                        "John",
                        "Doe",
                        "US"
                )
                .toCompletableFuture().get()
                .getBody().getCustomer().getFirstName();

        logger.info("Customer created: {}", firstName);
        client.close();
    }
}
