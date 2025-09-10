package com.ct.firsthand.importapi;

import com.commercetools.importapi.client.ProjectApiRoot;
import com.commercetools.importapi.models.common.ImportResourceType;
import com.commercetools.importapi.models.customers.CustomerAddressBuilder;
import com.commercetools.importapi.models.customers.CustomerImportBuilder;
import com.commercetools.importapi.models.importcontainers.ImportContainer;
import com.commercetools.importapi.models.importcontainers.ImportContainerDraftBuilder;
import com.commercetools.importapi.models.importoperations.ImportOperation;
import com.commercetools.importapi.models.importrequests.CustomerImportRequestBuilder;
import com.commercetools.importapi.models.importsummaries.OperationStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ImportExercise {

    private static final String containerKey = "myProject-customer-container-learn";
    private static final Logger logger = LoggerFactory.getLogger(ImportExercise.class);


    public static void main(String[] args) {
        ProjectApiRoot importApiRoot = ImportClientBuilder.createImportApiClient();

        //createImportContainer(importApiRoot);

        //isContainerExists(importApiRoot);

        //importCustomer(importApiRoot);

        //monitorImportStatus(importApiRoot);

        monitorContainerStatus(importApiRoot);

        importApiRoot.close();
    }

    static void createImportContainer(ProjectApiRoot importApiRoot) {
        String containerKey = "myProject-customer-container-learn";

        importApiRoot.importContainers()
                .post(
                        ImportContainerDraftBuilder
                                .of()
                                .key(containerKey)
                                .resourceType(ImportResourceType.CUSTOMER)
                                .build()
                )
                .executeBlocking();

    }

    static void isContainerExists(ProjectApiRoot importApiRoot) {

        List<ImportContainer> containerList = importApiRoot.importContainers()
                .get()
                .executeBlocking()
                .getBody()
                .getResults();

        for (ImportContainer ic : containerList) {
            if (ic.getKey().equals(containerKey)) {
                logger.info("Our container ({}) is ready!", containerKey);
            }
        }

    }

    static void importCustomer(ProjectApiRoot importApiRoot) {
        importApiRoot
                .customers()
                .importContainers()
                .withImportContainerKeyValue(containerKey)
                .post(
                        CustomerImportRequestBuilder
                                .of()
                                .resources(
                                        CustomerImportBuilder
                                                .of()
                                                .key("imported-customer-01")
                                                .firstName("Sam")
                                                .lastName("Davies")
                                                .email("sdavies@example.com")
                                                .password("secret123")
                                                .addresses(
                                                        CustomerAddressBuilder
                                                                .of()
                                                                .key("imported-customer-01-address")
                                                                .country("DE")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .executeBlocking();
    }

    static void monitorImportStatus(ProjectApiRoot importApiRoot) {
        List<ImportOperation> importOperations = importApiRoot
                .importContainers()
                .withImportContainerKeyValue(containerKey)
                .importOperations()
                .get()
                .executeBlocking()
                .getBody()
                .getResults();
        for (ImportOperation io : importOperations) {
            logger.info("Resource: {} State: {}", io.getResourceKey(), io.getState());
        }
    }


    static void monitorContainerStatus(ProjectApiRoot importApiRoot) {
        OperationStates operationStates = importApiRoot
                .importContainers()
                .withImportContainerKeyValue(containerKey)
                .importSummaries()
                .get()
                .executeBlocking()
                .getBody()
                .getStates();


        logger.info(
                "Processing: " + operationStates.getProcessing() +
                        "\nValidation failed: " + operationStates.getValidationFailed() +
                        "\nUnresolved: " + operationStates.getUnresolved() +
                        "\nWaiting for MasterVariant: " + operationStates.getWaitForMasterVariant() +
                        "\nImported: " + operationStates.getImported() +
                        "\nRejected: " + operationStates.getRejected()
        );

    }
}
