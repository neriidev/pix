# Pix Backend

Este é o backend para o projeto Pix.

## Pré-requisitos

- Java 17
- Maven
- Docker
- Docker Compose

## Executando o projeto localmente

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/neriidev/pix-backend.git
    cd pix-backend
    ```

2.  **Execute a aplicação:**

    ```bash
    mvn spring-boot:run
    ```

A aplicação estará disponível em `http://localhost:8080`.

## Executando o projeto com Docker Compose

1.  **Compile a aplicação:**

    ```bash
    mvn clean install
    ```

2.  **Execute com Docker Compose:**

    ```bash
    docker-compose up -d
    ```

A aplicação estará disponível em `http://localhost:8080`.

## Endpoints da API

### Carteiras (Wallets)

- **Criar Carteira**
  - **POST** `/wallets`
  - Cria uma nova carteira.
  - **Corpo da Requisição:** `WalletRequest`
  - **Corpo da Resposta:** `WalletResponse`

- **Registrar Chave PIX**
  - **POST** `/wallets/{id}/pix-keys`
  - Registra uma nova chave PIX para uma carteira.
  - **Corpo da Requisição:** `PixKeyRequest`
  - **Corpo da Resposta:** `PixKeyResponse`

- **Obter Saldo**
  - **GET** `/wallets/{id}/balance`
  - Obtém o saldo de uma carteira.
  - **Parâmetros de Consulta:**
    - `at` (opcional, `LocalDateTime`): A data e hora para obter o saldo.
  - **Corpo da Resposta:** `BalanceResponse`

- **Depósito**
  - **POST** `/wallets/{id}/deposit`
  - Deposita um valor em uma carteira.
  - **Corpo da Requisição:** `DepositRequest`

- **Saque**
  - **POST** `/wallets/{id}/withdraw`
  - Saca um valor de uma carteira.
  - **Corpo da Requisição:** `AmountRequest`

### PIX

- **Transferência Interna**
  - **POST** `/pix/transfers`
  - Realiza uma transferência interna entre duas chaves PIX.
  - **Cabeçalho da Requisição:** `Idempotency-Key`
  - **Corpo da Requisição:** `InternalTransferRequest`
  - **Corpo da Resposta:** `InternalTransferResponse`

- **Webhook**
  - **POST** `/pix/webhook`
  - Recebe uma notificação de webhook.
  - **Corpo da Requisição:** `WebhookRequest`

## Objetos

### Objetos de Requisição

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

### Objetos de Resposta

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

## Executando os testes localmente

```bash
mvn test
```

## Executando os testes com Docker Compose

Não é recomendado executar os testes com o Docker Compose, pois o ambiente de teste é configurado para usar um banco de dados H2 em memória. No entanto, se você deseja executar os testes em um banco de dados PostgreSQL, pode fazer o seguinte:

1.  **Comente a dependência H2 no `pom.xml`:**

    ```xml
    <!--
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
    -->
    ```

2.  **Execute os testes com o Docker Compose:**

    ```bash
    docker-compose run --rm app mvn test
    ```