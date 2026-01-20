# Coffee Ordering System Backend

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2%2B-green.svg)](https://spring.io/projects/spring-boot)
[![Microservices](https://img.shields.io/badge/Architecture-Microservices-blue.svg)](https://microservices.io/)

A robust and scalable backend for a coffee ordering platform, built using a **Microservices Architecture** with **Spring Boot** and **Spring Cloud**. This system handles user authentication, coffee menu management, order processing, and payment integration, utilizing an event-driven approach with **Apache Kafka**.

##  Features

* **Microservices Architecture**: Decoupled services for better scalability and maintenance.
* **Service Discovery**: Integrated **Netflix Eureka** for dynamic service registration and discovery.
* **API Gateway**: Centralized entry point using **Spring Cloud Gateway** for routing and load balancing.
* **Security**: JWT-based Authentication and Authorization.
* **Event-Driven**: Asynchronous communication between services using **Apache Kafka**.
* **Resilience**: Implements **Resilience4j Circuit Breakers** for fault tolerance in inter-service communication.
* **Payment Integration**: Secure payment processing with **Razorpay**.
* **Database**: Persistent data storage using **MySQL**.

##  Architecture Overview

The system consists of the following microservices:

| Service | Port | Description |
| :--- | :--- | :--- |
| **Eureka Server** | `8761` | Service Discovery Server. |
| **API Gateway** | `8080` | Entry point for all client requests. |
| **User Service** | `8082` | Manages user registration, login, and JWT generation. |
| **Coffee Service** | `8083` | Manages coffee inventory and menu items. |
| **Order Service** | `8081` | Handles order placement and status updates. |
| **Payment Service** | `8084` | Manages payment initiation and verification via Razorpay. |

##  Tech Stack

* **Language**: Java 21
* **Framework**: Spring Boot 3+, Spring Cloud (2025.x/2023.x)
* **Database**: MySQL
* **Messaging**: Apache Kafka
* **Build Tool**: Maven
* **Security**: Spring Security, JJWT (JSON Web Token)
* **Payment Gateway**: Razorpay
* **Inter-service Communication**: OpenFeign, RestTemplate

##  Getting Started

Follow these instructions to set up the project locally.

### Prerequisites

* **Java 21** SDK installed.
* **Maven** installed.
* **MySQL** running locally (Port 3306).
* **Apache Kafka** & **Zookeeper** running locally.

### Environment Configuration

Before running the services, you must configure the following environment variables on your machine or update the `application.yaml` files in each service.

| Variable | Description |
| :--- | :--- |
| `SQL_PASSWORD` | Your local MySQL root password. |
| `JWT_SECRET` | A secure secret key for signing JWT tokens. |
| `RAZORPAY_KEYID` | Your Razorpay API Key ID. |
| `RAZORPAY_SECRET` | Your Razorpay API Secret. |

### Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/hirenkhatri7/coffee-ordering-system-backend.git
    cd coffee-ordering-system-backend
    ```

2.  **Build the project**
    Navigate to each service directory (`eurekaServer`, `gateway`, `UserService`, etc.) and run:
    ```bash
    mvn clean install
    ```

### Running the System

To ensure proper functionality, start the services in the following order:

1.  **Infrastructure**: Ensure MySQL and Kafka are running.
2.  **Eureka Server**:
    ```bash
    cd eurekaServer
    mvn spring-boot:run
    ```
3.  **API Gateway**:
    ```bash
    cd gateway
    mvn spring-boot:run
    ```
4.  **Microservices** (Open separate terminals for each):
    * **User Service**: `mvn spring-boot:run`
    * **Coffee Service**: `mvn spring-boot:run`
    * **Order Service**: `mvn spring-boot:run`
    * **Payment Service**: `mvn spring-boot:run`

##  API Usage

Once all services are running, you can access the API via the Gateway at `http://localhost:8080`.

### **Auth & Users**
* `POST /auth/register` - Register a new user.
* `POST /auth/login` - Login and get JWT token.

### **Coffee**
* `GET /coffee/` - List all coffees.
* `POST /coffee/` - Add a new coffee.

### **Orders**
* `POST /orders/` - Place an order (Requires `Authorization: Bearer <token>`).
* `GET /orders/{userId}` - Get orders for a user.

### **Payments**
* `POST /payments/initiate` - Initiate a Razorpay payment.
* `POST /payments/verify` - Verify payment signature.

##  Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1.  Fork the project.
2.  Create your feature branch (`git checkout -b feature/AmazingFeature`).
3.  Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4.  Push to the branch (`git push origin feature/AmazingFeature`).
5.  Open a Pull Request.

##  Support

If you encounter any issues or have questions, please file an issue on the [GitHub Issues](https://github.com/hirenkhatri7/coffee-ordering-system-backend/issues) page.


---
*This project is for educational purposes demonstrating microservices patterns with Spring Boot.*