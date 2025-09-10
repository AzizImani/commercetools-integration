package com.ct.firsthand.restapi;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.BaseAddress;
import com.commercetools.api.models.customer.CustomerSetCustomerGroupActionBuilder;
import com.commercetools.api.models.customer.CustomerUpdateBuilder;
import com.commercetools.api.models.customer_group.CustomerGroupResourceIdentifierBuilder;
import com.ct.firsthand.client.DefaultClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RestExercise {

    private static final Logger logger = LoggerFactory.getLogger(RestExercise.class);

    private static final String customerKey = "john-doe-customer";
    private static final String customerGroupKey = "vip-customer-group";

    private final ProjectApiRoot client;
    private final CustomerService customerService;


    public RestExercise() {
        try {
            client = DefaultClientBuilder.createApiClient();
            customerService = new CustomerService(client);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        RestExercise customerExercise = new RestExercise();

        // getCreatedCustomer(customerExercise.customerService);

        // assignCustomerToGroup(customerExercise.customerService);

        // deleteCustomer(customerExercise.customerService);

        // updatedCustomerWithCustomerGroup(customerExercise.client);

        // getCustomersInFareham(customerExercise.client);

        // getMatchingVariants(customerExercise.client);

        // getMatchingVariantsMultipleConditions(customerExercise.client);

        getCustomersInCity(customerExercise.client);

        customerExercise.client.close();
    }

    private static void getCustomersInCity(ProjectApiRoot client) {
        final String customerId = "4e085d3c-dea3-4f53-9c06-38e7e71d9220";

        final String cityName = client
                .customers()
                .withId(customerId)
                .get()
                .executeBlocking()
                .getBody()
                .withCustomer(customer ->
                        customer
                                .getAddresses()
                                .stream()
                                .filter(a -> a.getId().equals(customer.getDefaultShippingAddressId()))
                                .findFirst()
                                .map(BaseAddress::getCity)
                                .orElse("Fareham")
                );

        logger.info(
                "Customers in the same city : {}", client.customers()
                        .get()
                        .withWhere("addresses(city = :city)")
                        .addPredicateVar("city", cityName)
                        .executeBlocking()
                        .getBody()
                        .getResults()
                        .size()
        );
    }

    private static void getMatchingVariantsMultipleConditions(ProjectApiRoot client) {
        logger.info(
                "Products available in Red: {}", client
                        .productProjections()
                        .get()
                        .withWhere("variants(attributes(name = \"color\"))")
                        .addWhere("variants(attributes(value(en = \"red\")))")
                        .executeBlocking()
                        .getBody()
                        .getResults()
                        .size()
        );

    }

    private static void getMatchingVariants(ProjectApiRoot client) {
        logger.info(
                "Products matching {}", client
                        .productProjections()
                        .get()
                        .withWhere(
                                "variants(attributes(name = \"colorlabel\" and value(en-GB = \"Black\")))"
                        )
                        .executeBlocking()
                        .getBody()
                        .getResults()
                        .size()
        );
    }

    private static void getCustomersInFareham(ProjectApiRoot client) {
        logger.info(
                "Customers in Fareham: {}", client.customers()
                        .get()
                        .withWhere("addresses(city = \"Fareham\")")
                        .executeBlocking()
                        .getBody()
                        .getResults()
                        .size()
        );
    }

    private static void getCreatedCustomer(CustomerService customerService) throws ExecutionException, InterruptedException {
        String email = customerService.getCustomerByKey("john-doe-customer")
                .toCompletableFuture().get()
                .getBody().getEmail();

        logger.info("Customer fetch: {}", email);
    }

    private static void assignCustomerToGroup(CustomerService customerService) throws ExecutionException, InterruptedException {
        logger.info("Customer assigned to group: {}",
                customerService
                        .assignCustomerToCustomerGroup(
                                1L, "john-doe-customer", "vip-customer-group"
                        )
                        .toCompletableFuture().get()
                        .getBody().getEmail()
        );
    }

    private static void deleteCustomer(CustomerService customerService) throws ExecutionException, InterruptedException {
        logger.info("Customer deleted: {}", customerService
                .deleteCustomerByKey(
                        "john-doe-customer", 2L
                )
                .toCompletableFuture().get()
                .getBody().getEmail()
        );
    }

    private static void updatedCustomerWithCustomerGroup(ProjectApiRoot client) throws ExecutionException, InterruptedException {
        logger.info("Updated customer with the customer group {}", client.customers()
                .withKey(customerKey)
                .get()
                .execute()
                .thenComposeAsync(customerApiHttpResponse ->
                        client.customers()
                                .withKey(customerKey)
                                .post(
                                        CustomerUpdateBuilder.of()
                                                .version(customerApiHttpResponse.getBody().getVersion())
                                                .actions(
                                                        CustomerSetCustomerGroupActionBuilder.of()
                                                                .customerGroup(
                                                                        CustomerGroupResourceIdentifierBuilder.of()
                                                                                .key(customerGroupKey).build())
                                                                .build())
                                                .build()
                                )
                                .execute()
                ).get().getBody().getId());
    }
}
