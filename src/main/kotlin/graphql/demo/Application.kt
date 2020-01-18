package graphql.demo

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("graphql.demo")
                .mainClass(Application.javaClass)
                .start()
    }
}
