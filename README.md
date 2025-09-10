# Commercetools Integration

A Java-based integration project demonstrating Commercetools platform capabilities, developed as part of the Commercetools Developer Essentials learning path.

## Overview

This project showcases various Commercetools integrations including:

- GraphQL API operations and queries
- Import API functionality for bulk data operations
- REST API interactions for customer management
- Advanced querying and data manipulation techniques

## Project Structure

```
src/main/java/com/ct/firsthand/
├── client/
│   └── DefaultClientBuilder.java     # API client configuration
├── graphql/
│   └── GraphqlExercise.java          # GraphQL operations and queries
├── importapi/
│   ├── ImportClientBuilder.java      # Import API client setup
│   └── ImportExercise.java           # Import operations and monitoring
└── restapi/
    ├── CustomerCreate.java           # Customer creation operations
    ├── CustomerFetch.java            # Customer retrieval operations
    └── CustomerService.java          # Customer service layer
```

## Technologies Used

- Java - Primary programming language
- Commercetools Java SDK - Official SDK for Commercetools platform
- Maven - Dependency management and build tool
- GraphQL - Query language for flexible data fetching
- REST API - Traditional API operations

## Prerequisites

- Java 21
- Maven 3.6+
- Commercetools project credentials
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Setup

1. Clone the repository
   ```bash
   git clone https://github.com/AzizImani/commercetools-integration.git
   cd commercetools-integration
   ```

2. Configure your Commercetools credentials (application.properties or environment variables):
   ```properties
   # Application properties
   commercetools.project.key=your-project-key
   commercetools.client.id=your-client-id
   commercetools.client.secret=your-client-secret
   ```

3. Install dependencies
   ```bash
   mvn clean install
   ```

## Key Features

### GraphQL Operations
- Advanced product queries
- Custom field handling
- Efficient data fetching with GraphQL

### Import API Integration
- Bulk data import operations
- Import container management
- Real-time import status monitoring
- Error handling and validation

### Customer Management
- Customer creation and updates
- Customer data retrieval
- Customer service operations

## Related Resources

- [Commercetools Developer Center](https://docs.commercetools.com/)
- [Commercetools Java SDK Documentation](https://docs.commercetools.com/sdk)
- [GraphQL Learning Resources](https://graphql.org/learn/)
- [Developer Essentials Learning Path](https://docs.commercetools.com/learning)

## License

This project is created for educational purposes as part of the Commercetools Developer Essentials program.

---
This project is part of the *Commercetools Developer Essentials* learning path, designed to provide hands-on experience with the Commercetools platform.
