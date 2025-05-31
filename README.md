# NONSENSE-generator

## 1. High-Level Description

**NONSENSE-generator** is a Java application designed to generate completely random sentences. By unpredictably combining nouns, adjectives, and verbs, the application creates grammatically structured but semantically meaningless phrases. This tool is useful for testing, simulation, or simply for fun.

## 2. Installation and Launch

### Requirements

* **Java Development Kit (JDK)**: Java 21 or higher. Download it from [Oracle website](https://www.oracle.com/it/java/technologies/downloads/#java24).

* **Apache Maven**: Maven 4.0.0 is required for managing dependencies and building the project. Download it from the [Maven website](https://maven.apache.org/download.cgi).

### Installation Steps

1. **Clone the repository and insert the apiKey**:

   ```bash
   git clone https://github.com/Ceschissimo04/NONSENSE-generator.git
   cd NONSENSE-generator
   echo { "apiKey":"YOUR_API_KEY" } > config.json  
   ```

2. **Build and run the application**:

    **windows user**
   ```bash
   .\mvnw clean install
   .\mvnw spring-boot:run
   ```

   **linux and macOS user**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   


## 3. Runtime Environment and Constraints

* **Operating System**: Compatible with Windows, macOS, and Linux
* **Java Version**: Java 21 or higher
* **Maven**: Ensure Maven is installed and properly configured



## 4. Reused Functions from Existing Libraries

The NONSENSE-generator project leverages several third-party libraries to streamline development and enhance functionality. Key libraries include:

* **Spring Boot**: Simplifies the development of stand-alone, production-grade Spring-based applications with minimal configuration.
* **Apache Commons Lang**: Provides useful methods for string manipulation and other common tasks
* **Google Guava**: Offers a rich set of utilities for collections, caching, and other data structures
* **JUnit**: A widely used testing framework for Java that enables writing and running repeatable automated tests.
* **SLF4J e Logback**: SLF4J is a simple facade for various logging frameworks; Logback is a robust logging framework often used as the default implementation for SLF4J.
* **Jackson**: A high-performance library for processing JSON data, including parsing and generating JSON in Java.
* **Maven Wrapper**: Allows a project to include a script and a small JAR to ensure the correct version of Maven is used, simplifying builds for new contributors.



## 5. External APIs Used

The NONSENSE-generator project uses two APIs from **Google Cloud**:
* **Analyzing Syntax**: Inspects the structure of the given text. Syntactic Analysis breaks up the given text into a series of tokens and provides linguistic information about those tokens ([Documentation](https://cloud.google.com/natural-language/docs/analyzing-syntax)).
* **Moderate text**: Analyzes a text against a list of safety attributes, which include "harmful categories" and topics that may be considered sensitive ([Documentation](https://cloud.google.com/natural-language/docs/moderating-text)).


## Other docs ##
* User stories ==> [link](https://studenti-team-l2ldmeti.atlassian.net/jira/software/projects/MBA/list)
* Design document ==> [link](docs\DocumentoDiDesign.md)
* Unit test report ==> [link](https://html-preview.github.io/?url=https://github.com/Ceschissimo04/NONSENSE-generator/blob/main/docs/report/surefire.html)
