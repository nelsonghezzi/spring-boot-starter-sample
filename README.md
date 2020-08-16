# Spring Boot Starter Sample

This project contains an example on how to build a custom Spring Boot Starter.

You can see the starter in action in [this project](https://github.com/nelsonghezzi/spring-boot-starter-consumer).

The code in this project is based on [this blog](https://reflectoring.io/spring-boot-starter/), and the [Spring docs](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-developing-auto-configuration) on Autoconfigurations.

[This article](https://codeboje.de/create-spring-boot-starter/) also contains useful guidance.

## Project Structure

The project is a multi-module Maven project, containing a root `pom.xml` inheriting from `spring-boot-starter-parent` and the following submodules:

* [event-publisher](event-publisher): a sample library with an `EventPublisher` abstraction and a dummy implementation to pubish "events" to registered listeners.
* [event-publisher-autoconfigure](event-publisher-autoconfigure): the Autoconfiguration module which exposes a Spring `@Configuration` for the `EventPublisher`.
* [event-publisher-starter](event-publisher-starter): the Starter which packages glues the previous modules as a single reference and is used in the [consumer project's pom.xml](https://github.com/nelsonghezzi/spring-boot-starter-consumer/blob/master/pom.xml#L27-L31). 

## Building

Just run the next command to compile, package and store the artifacts in the local maven repository:

```sh
mvn clean install
```

## Highlights

### Automatic registration for the `EventPublisherAutoConfiguration`

The heart of the Autoconfiguration relies on the [`META-INF/spring.factories`](event-publisher-autoconfigure/src/main/resources/META-INF/spring.factories) file, which registers the `EventPublisherAutoConfiguration` class as a Spring Boot Configuration.

### Use of `spring-boot-configuration-processor`

This annotation processor scans the classes annotated with `@ConfigurationProperties` to generate a `META-INF/spring-configuration-metadata.json` file, which documents the properties that can be used to configure the Starter.

The resulting file is combined with the custom [`META-INF/additional-spring-configuration-metadata.json`](event-publisher-autoconfigure/src/main/resources/META-INF/additional-spring-configuration-metadata.json) file that adds additional configuration documentation.

### Use of `spring-boot-autoconfigure-processor`

This annotation processor scans some of the `@ConditionalOn...` configuration metadata to produce a `META-INF/spring-autoconfigure-metadata.properties` file that allows Spring Boot to filter the Autoconfiguration without having to load and evaluate the annotations at runtime.

### Autoconfiguration Tests

The project also features [unit tests](event-publisher-autoconfigure/src/test/java/org/example/configuration/EventPublisherAutoConfigurationTests.java) for the Autoconfiguration using the `WebApplicationContextRunner` (because as an example, the Autoconfiguration has the `@ConditionalOnWebApplication` annotation).

These tests validate that:

* The Autoconfiguration provides the `EventPublisher` Bean.
* The Autoconfiguration is correctly disabled with the corresponding property.
* In the presence of a user-defined `EventPublisher` Bean the Autoconfiguration correctly backs off.
* User-defined `EventListener`s are correctly picked up by the Autoconfiguration and passed down to the Bean instance.
* Typed `ConfigurationProperties` are passed down to the Bean instance.
