# Ticket API Jakarta/Java EE8

Jakarta EE 8 Sample

## What's it?

It's a RESTfull API project to help you to bootstrap a new Jakarta/Java EE8 application.


## Tech

- JDK 11
- Maven 3.6

#### Server

- Wildfly 19 - JEE8

#### Database

- H2(ExampleDS inside Wildfly)

#### Development

- Lombok 1.18 (to reduce boilerplate code)
- JAX-RS 2.1
- CDI 2.0
- Commons Annotations 1.3
- Security 1.0
- JPA 2.2
- EJB 3.2
- Bean Validation 2.0
- Transactions 1.3

## Folder Structure

```
src/main/java/
└── br.com.iwakoshi.ticket 			// base package
    ├── bootstrap				// runs at startup
    ├── config					// configs outside the business logic
    │   ├── auth					// authentication / authorization
    │   ├── exception					// exception handling
    │   ├── jpa						// JPA Hints
    │   ├── json					// resource views and json provider
    │   └── rest					// activates JAX-RS
    ├── event					// **START HERE**: create your resource, service, repository and model for your business
    └── util					// utilities
```