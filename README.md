# Micronaut GraphQL Demo

Uses Hacker News API by recursively requesting the `kids` section and caching it in in-memory database, which is then queried via GraphQL.

This project demonstrates:
* Micronaut
* GraphQL

## GraphQL
The console is hosted at [/graphiql](http://localhost:8080/graphiql).
##### Query
```graphql
{
  allNews(id: 126809) {
    id
    text
    comments {
      id
   }
  }
}
```

##### Mutation
```graphql
mutation{
  editNewsVM(id: "126822", text:"Hello world"){
    id,
    text
  }
}
```


---
## References
* [Micronaut GraphQL Integration](https://micronaut-projects.github.io/micronaut-graphql/latest/guide/)
* [Building GraphQL APIs with Kotlin, Spring Boot, and MongoDB](https://auth0.com/blog/building-graphql-apis-with-kotlin-spring-boot-and-mongodb/)
* [Access a database with JPA and Hibernate](https://guides.micronaut.io/micronaut-data-access-jpa-hibernate/guide/index.html)
* [Hacker News API](https://github.com/HackerNews/API)
* [graphql-spqr](https://github.com/leangen/graphql-spqr)
