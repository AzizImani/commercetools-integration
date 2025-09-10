package com.ct.firsthand.graphql;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.graphql.api.GraphQL;
import com.commercetools.graphql.api.GraphQLResponse;
import com.commercetools.graphql.api.types.CustomerQueryResult;
import com.ct.firsthand.client.DefaultClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

public class GraphqlExercise {

    private static final Logger logger = LoggerFactory.getLogger(GraphqlExercise.class);

    private final ProjectApiRoot client;

    GraphqlExercise() {
        try {
            client = DefaultClientBuilder.createApiClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        GraphqlExercise exercise = new GraphqlExercise();

        GraphQLResponse<CustomerQueryResult> response = exercise.client
                .graphql()
                .query(
                        GraphQL
                                .customers(query ->
                                        query
                                                .sort((Collections.singletonList("id asc")))
                                                .where("firstName=\"Jennifer\"")
                                )
                                .projection(root -> root.results().firstName().lastName().email())
                )
                .executeBlocking()
                .getBody();

        logger.info("Total Customers: {}", response.getData().getTotal());
        logger.info("First" + "\t" + "Last" + "\t" + "Email");
        logger.info("-----" + "\t" + "----" + "\t" + "-----");
        response
                .getData()
                .getResults()
                .forEach(result ->
                        logger.info("{}\t{}\t{}", result.getFirstName(), result.getLastName(), result.getEmail()
                        )
                );

        exercise.client.close();
    }
}
