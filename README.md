# Exportation Panelera

A Java Swing application for managing exportation processes.

## Project Structure

The project follows the MVC (Model-View-Controller) pattern:

- `Model/`: Data Transfer Objects (DTOs) and business logic
- `View/`: Swing UI components and forms
- `Controlls/`: Controllers handling business logic and user interactions

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

## Building the Project

1. Clone the repository
2. Run `mvn clean install` to build the project
3. The executable JAR will be created in the `target` directory

## Running the Application

After building, run the application using:

```bash
java -jar target/exportation-panelera-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Features

- Exportation Information Management
- Delivery Information Management
- Signing Process Management

## Logging

The application uses SLF4J with Logback for logging. Logs are stored in the `logs` directory.

## License

[Add your license information here]
