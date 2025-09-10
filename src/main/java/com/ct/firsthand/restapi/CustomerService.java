package com.ct.firsthand.restapi;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.AddressDraftBuilder;
import com.commercetools.api.models.customer.Customer;
import com.commercetools.api.models.customer.CustomerDraftBuilder;
import com.commercetools.api.models.customer.CustomerSetCustomerGroupAction;
import com.commercetools.api.models.customer.CustomerSetCustomerGroupActionBuilder;
import com.commercetools.api.models.customer.CustomerSignInResult;
import com.commercetools.api.models.customer.CustomerUpdateBuilder;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.customer_group.CustomerGroupResourceIdentifierBuilder;
import io.vrap.rmf.base.client.ApiHttpResponse;

import java.util.concurrent.CompletableFuture;

public class CustomerService {

  final ProjectApiRoot apiRoot;

  public CustomerService(final ProjectApiRoot client) {
    this.apiRoot = client;
  }

  public CompletableFuture<ApiHttpResponse<CustomerSignInResult>> createCustomer(
       final String email,
       final String password,
       final String customerKey,
       final String firstName,
       final String lastName,
       final String country) {

        return apiRoot.customers()
           .post(
              CustomerDraftBuilder.of()
                .firstName(firstName)
                .lastName(lastName)
                .key(customerKey)
                .email(email)
                .password(password)
                .addresses(
                    AddressDraftBuilder.of()
                      .firstName(firstName)
                      .lastName(lastName)
                      .key(customerKey + "-home")
                      .country(country)
                      .build()
                )
              .build()
            )
            .execute();
        }

    public CompletableFuture<ApiHttpResponse<Customer>> getCustomerByKey(String customerKey) {
        return
                apiRoot
                        .customers()
                        .withKey(customerKey)
                        .get()
                        .execute();
    }

    public CompletableFuture<ApiHttpResponse<Customer>> assignCustomerToCustomerGroup(long version,
                                                                                      String customerKey,
                                                                                      String customerGroupKey) {
        CustomerSetCustomerGroupAction action = CustomerSetCustomerGroupActionBuilder.of()
                .customerGroup(CustomerGroupResourceIdentifierBuilder.of()
                        .key(customerGroupKey)
                        .build()
                ).build();
        return apiRoot.customers()
                        .withKey(customerKey)
                        .post(CustomerUpdateBuilder.of()
                                .version(version)
                                .actions(action)
                                .build()
                        )
                        .execute();
    }

    public CompletableFuture<ApiHttpResponse<Customer>> deleteCustomerByKey(String customerKey, long version) {
        return
                apiRoot
                        .customers()
                        .withKey(customerKey)
                        .delete()
                        .withVersion(version)
                        .execute();
    }


    public CompletableFuture<ApiHttpResponse<Customer>> assignCustomerToCustomerGroup(
            final ApiHttpResponse<Customer> customerApiHttpResponse,
            final ApiHttpResponse<CustomerGroup> customerGroupApiHttpResponse) {

        // Step 1: Unpack the objects from the previous requests
        final Customer customer = customerApiHttpResponse.getBody();
        final CustomerGroup customerGroup = customerGroupApiHttpResponse.getBody();

        // Step 2: Add some logging

        // Step 3: Prepare the next request
        return
                apiRoot
                        .customers()
                        .withKey(customer.getKey())
                        .post(CustomerUpdateBuilder.of()
                                .version(customer.getVersion())
                                .actions(
                                        CustomerSetCustomerGroupActionBuilder.of()
                                                .customerGroup(CustomerGroupResourceIdentifierBuilder.of()
                                                        .key(customerGroup.getKey())
                                                        .build())
                                                .build()
                                )
                                .build())
                        .execute();
    }

}
