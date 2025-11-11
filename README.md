# Ecommerce-bk

## Introduction

A backend application for an e-commerce website to exchange craft items developing by using Spring Boot framework.
It provides some of the necessary and basic feature of an e-commerce website like:

- Simple authentication using username and password with Redis to store user sessions.
- Role-based authorization.
- Features of seller including products management, orders management.
- Features of buyer including seller registration, product search, buying products.
- Shopping carts with Redis.
- VNPay integration.
- Swagger to documentation.

## Technologies

- Java 17.
- Spring Boot 3.3.2.
- Hibernate.
- MySQL.
- Redis.
- Swagger.

## How to run the project

1. Clone this repository
2. Navigate to the project directory:
`cd ecommerce-bk`
3. Update `application.properties` with your own database (MySQL, Redis) connection, Cloudinary config, Spring Email config and VNPay config.
4. Import data.
5. Build docker image.
`docker build -t ecommerce`
6. Run container.
`docker run -p 8080:8080 ecommerce`


## Continued development

- Realtime chat
- Unit Testing
- Deploy into a cloud service.