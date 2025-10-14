# Pix Backend

This is the backend for the Pix project.

## Prerequisites

- Java 17
- Maven
- Docker
- Docker Compose

## Running the project locally

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/neriidev/pix-backend.git
    cd pix-backend
    ```

2.  **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

The application will be available at `http://localhost:8080`.

## Running the project with Docker Compose

1.  **Build the application:**

    ```bash
    mvn clean install
    ```

2.  **Run with Docker Compose:**

The application will be available at `http://localhost:8080`.

## API Endpoints

### Wallets

- **Create Wallet**
  - **POST** `/wallets`
  - Creates a new wallet.
  - **Request Body:** `WalletRequest`
  - **Response Body:** `WalletResponse`

- **Register PIX Key**
  - **POST** `/wallets/{id}/pix-keys`
  - Registers a new PIX key for a wallet.
  - **Request Body:** `PixKeyRequest`
  - **Response Body:** `PixKeyResponse`

- **Get Balance**
  - **GET** `/wallets/{id}/balance`
  - Gets the balance of a wallet.
  - **Query Parameters:**
    - `at` (optional, `LocalDateTime`): The date and time to get the balance at.
  - **Response Body:** `BalanceResponse`

- **Deposit**
  - **POST** `/wallets/{id}/deposit`
  - Deposits an amount into a wallet.
  - **Request Body:** `DepositRequest`

- **Withdraw**
  - **POST** `/wallets/{id}/withdraw`
  - Withdraws an amount from a wallet.
  - **Request Body:** `AmountRequest`

### PIX

- **Internal Transfer**
  - **POST** `/pix/transfers`
  - Performs an internal transfer between two PIX keys.
  - **Request Header:** `Idempotency-Key`
  - **Request Body:** `InternalTransferRequest`
  - **Response Body:** `InternalTransferResponse`

- **Webhook**
  - **POST** `/pix/webhook`
  - Receives a webhook notification.
  - **Request Body:** `WebhookRequest`

## Objects

### Request Objects

- `WalletRequest`
  ```json
  {
    "id": 1,
    "balance": 100.00
  }
  ```

- `PixKeyRequest`
  ```json
  {
    "key": "user@example.com",
    "type": "EMAIL"
  }
  ```

- `DepositRequest`
  ```json
  {
    "amount": 50.00
  }
  ```

- `AmountRequest`
  ```json
  {
    "amount": 20.00
  }
  ```

- `InternalTransferRequest`
  ```json
  {
    "fromPixKey": "user1@example.com",
    "toPixKey": "user2@example.com",
    "amount": 10.00
  }
  ```

- `WebhookRequest`
  ```json
  {
    "eventId": "some-event-id",
    "endToEndId": "some-end-to-end-id",
    "status": "COMPLETED"
  }
  ```

### Response Objects

- `WalletResponse`
  ```json
  {
    "id": 1,
    "balance": 100.00
  }
  ```

- `PixKeyResponse`
  ```json
  {
    "id": 1,
    "key": "user@example.com",
    "type": "EMAIL"
  }
  ```

- `BalanceResponse`
  ```json
  {
    "balance": 100.00,
    "retrievedAt": "2025-10-14T17:45:39.123456"
  }
  ```

- `InternalTransferResponse`
  ```json
  {
    "id": 1,
    "endToEndId": "some-end-to-end-id",
    "amount": 10.00,
    "status": "COMPLETED",
    "createdAt": "2025-10-14T17:45:39.123456"
  }
  ```

## Running the tests locally

```bash
mvn test
```

## Running the tests with Docker Compose

It is not recommended to run the tests with Docker Compose, as the test environment is configured to use an in-memory H2 database. However, if you want to run the tests against a PostgreSQL database, you can do the following:

1.  **Comment out the H2 dependency in `pom.xml`:**

    ```xml
    <!--
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
    -->
    ```

2.  **Run the tests with Docker Compose:**

    ```bash
    docker-compose run --rm app mvn test
    ```
