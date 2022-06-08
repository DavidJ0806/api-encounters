# Super Health API

## Getting Started

### Start the Server

Right click AppRunner, and select "Run 'AppRunner.main()'"

### Connections

By default, this service starts up on port 8085 and accepts cross-origin requests from `*`.

#### JDK

You must have a JDK installed on your machine.

#### Postgres

This server requires that you have Postgres installed and running on the default Postgres port of 5432. It requires that you have a database created on the server with the name of `postgres`
- Your username should be `postgres`
- Your password should be `root`

#### Postman Collection

This is a JSON link to the postman collection that will guide you with interacting with the databases endpoints
https://www.getpostman.com/collections/410e8e69f9d8e79f9570

### Logging

Logs are included in the application console, and a log file is generated under `/super-health-API-v1/` when the program is run.

#### Testing

To run the test coverage of the project, navigate to the path `src/test/java` right-click and select "Run 'All Tests' With Coverage"

#### Swagger Docs

For swagger documentation on the project's endpoints, run the project then visit http://localhost:8085/swagger-ui.html#/

#### Linting the Project

If your IntelliJ's Code Style is not configured, navigate to Google's [intellij-java-google-style.xml](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml) file and import it to your IntelliJ's Code Style for Java.
If your Intellij is already configured, use ctrl + alt + l to automatically lint the project. Alternatively,
right-click the package you wish to lint, and click "Reformat Code."