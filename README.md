# QuotationRequest

_The service provides functionality to integrate with Lime CRM in order to create quotation requests and read metadata_

## Getting Started

### Prerequisites

- **Java 21 or higher**
- **Maven**
- **Git**
- **[Dependent Microservices](#dependencies)**

### Installation

1. **Clone the repository:**

```bash
git clone https://github.com/Sundsvallskommun/api-service-quotation-request.git
cd api-service-quotation-request
```

2. **Configure the application:**

   Before running the application, you need to set up configuration settings.
   See [Configuration](#configuration)

   **Note:** Ensure all required configurations are set; otherwise, the application may fail to start.

3. **Ensure dependent services are running:**

   If this microservice depends on other services, make sure they are up and accessible. See [Dependencies](#dependencies) for more details.

4. **Build and run the application:**

   - Using Maven:

```bash
mvn spring-boot:run
```

- Using Gradle:

```bash
gradle bootRun
```

## Dependencies

This microservice does not depend on any other internal services. However, it does depend on external services for the provider(s) it intends to use:

- **Lime**
  - **Purpose:** CRM system (Stadsbacken customers)
  - **Repository:** Service is provided by third party (Stadsbacken)

## API Documentation

Access the API documentation via:

- **Swagger UI:** [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## Usage

### API Endpoints

See the [API Documentation](#api-documentation) for detailed information on available endpoints.

### Example Request

```bash

curl -X 'POST' \
  'http://localhost:8080/2281/quotation-request' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "Installation av elnät",
  "note": "Beskrivning av installationen",
  "helpdeskId": "3601",
  "officeId": "2701",
  "contactDetails": {
    "firstName": "Joe",
    "surname": "Doe",
    "phoneNumber": "079 1234567",
    "emailAddress": "joe.doe@test.se"
  }
}'
```

## Configuration

Configuration is crucial for the application to run successfully. Ensure all necessary settings are configured in `application.yml`.

### Key Configuration Parameters

- **Server Port:**

```yaml
server:
  port: 8080
```

- **External Service URLs**

```yaml
integration:
  lime:
    apiKey: <api-key>
    url: <base-url>
```

## Status

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-quotation-request&metric=alert_status)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-quotation-request)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-quotation-request&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-quotation-request)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-quotation-request&metric=security_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-quotation-request)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-quotation-request&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-quotation-request)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-quotation-request&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-quotation-request)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Sundsvallskommun_api-service-quotation-request&metric=bugs)](https://sonarcloud.io/summary/overall?id=Sundsvallskommun_api-service-quotation-request)

## 

Copyright (c) 2025 Sundsvalls kommun
