package graphql.demo

import graphql.GraphQL
import graphql.demo.graphql.HackerNewsFetcher
import graphql.demo.graphql.HackerNewsMutator
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.core.io.ResourceResolver
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Singleton

@Factory
class GraphQLFactory {

    @Bean
    @Singleton
    fun graphQL(resourceResolver: ResourceResolver, dataFetcher: HackerNewsFetcher, mutator: HackerNewsMutator): GraphQL {

        val schemaParser = SchemaParser()
        val schemaGenerator = SchemaGenerator()

        // Parse the schema.
        val typeRegistry = TypeDefinitionRegistry()
        typeRegistry.merge(schemaParser.parse(BufferedReader(
                InputStreamReader(resourceResolver.getResourceAsStream("classpath:schema.graphqls").get()))
        ))

        // Create the runtime wiring.
        val runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query") { typeWiring ->
                    typeWiring.dataFetcher("allNews", dataFetcher)
                }.type("Mutation") { typeWiring ->
                    typeWiring.dataFetcher("editNewsVM", mutator)
                }.build()

        // Create the executable schema.
        val graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring)

        // Return the GraphQL bean.
        return GraphQL.newGraphQL(graphQLSchema).build()
    }
}
