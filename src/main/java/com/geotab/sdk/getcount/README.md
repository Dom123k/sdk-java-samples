# Get Count

 This example demonstrates how to get count of entities.

Steps:

1. Authenticate a user via login, password, database and server using the Geotab API object.
1. Enter the name of the entity to get the count of.

## Prerequisites

The sample application requires:

- JDK 1.8 or higher
- Maven 3.6.*

## Getting started

```shell
> git clone https://github.com/Geotab/sdk-java-samples.git sdk-java-samples
> cd sdk-java-samples
> mvn clean install
> cd target/
> WINDOWS:  java -cp 'sdk-java-samples.jar;./lib/*' com.geotab.sdk.getcount.GetCountApp 'my.geotab.com' 'database' 'user@email.com' 'password' 
> LINUX:    java -cp 'sdk-java-samples:./lib/*' com.geotab.sdk.getcount.GetCountApp 'my.geotab.com' 'database' 'user@email.com' 'password' 
```