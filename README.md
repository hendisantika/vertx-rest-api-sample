# Vert.x REST API Sample

[![Java CI with Maven](https://github.com/hendisantika/vertx-rest-api-sample/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/vertx-rest-api-sample/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
[![Vert.x](https://img.shields.io/badge/vert.x-5.2.2-purple.svg)](https://vertx.io)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-336791?logo=postgresql&logoColor=white)
[![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)](.github/dependabot.yml)

A reactive CRUD REST API for **books**, built with [Eclipse Vert.x 5](https://vertx.io) on **JDK 25**,
backed by PostgreSQL and versioned with Flyway migrations.

## Tech Stack

| Component        | Version  |
|------------------|----------|
| Java (Temurin)   | 25 (LTS) |
| Eclipse Vert.x   | 5.2.2    |
| Reactive PG client / SQL templates | Vert.x 5.2.2 |
| Flyway           | 13.12.0  |
| PostgreSQL       | 17       |
| Jackson (BOM)    | 2.22.4   |
| Lombok           | 1.18.48  |
| JUnit Jupiter    | 6.1.3    |
| Maven (wrapper)  | 3.10.0   |

## Project Structure

```
src/main/java/id/my/hendisantika/vertx_rest_api_sample
├── Main.java                 # Application entry point (deploys MainVerticle)
├── verticle
│   ├── MainVerticle.java     # Deploys MigrationVerticle, then ApiVerticle
│   ├── MigrationVerticle.java# Runs Flyway migrations on a worker thread
│   └── ApiVerticle.java      # HTTP server + routes
├── api
│   ├── router/BookRouter.java
│   ├── handler/BookHandler.java, ErrorHandler.java
│   ├── service/BookService.java
│   ├── repository/BookRepository.java
│   └── model/Book*.java
└── util                      # Config, DB pool, query & response helpers
src/main/resources
├── application.properties    # DB & server configuration
└── db/migration              # Flyway SQL migrations
```

## Prerequisites

- JDK 25 (e.g. [Eclipse Temurin](https://adoptium.net/))
- Docker (for PostgreSQL), or a local PostgreSQL instance

The Maven wrapper (`./mvnw`) is included, so a local Maven installation is not required.

## Configuration

`src/main/resources/application.properties`:

```properties
datasource.host=localhost
datasource.port=5432
datasource.database=book_db
datasource.username=yu71
datasource.password=53cret
server.port=8080
```

## Running Locally

1. Start PostgreSQL:

   ```shell
   docker compose up -d db
   ```

2. Run the application (Flyway migrations are applied automatically on startup):

   ```shell
   ./mvnw clean compile exec:java
   ```

   or build and run the fat jar:

   ```shell
   ./mvnw clean package
   java -jar target/vertx-rest-api-sample-1.0.0-fat.jar
   ```

The API is available at `http://localhost:8080/api/v1`.

## Testing

```shell
./mvnw clean test
```

A JaCoCo coverage report is generated at `target/site/jacoco/index.html`.

## API Endpoints

| Method   | Endpoint               | Description                           |
|----------|------------------------|---------------------------------------|
| `GET`    | `/api/v1/books`        | List books (`?page=1&limit=20`)       |
| `GET`    | `/api/v1/books/{id}`   | Get a book by id                      |
| `POST`   | `/api/v1/books`        | Create a book                         |
| `PUT`    | `/api/v1/books/{id}`   | Update a book                         |
| `DELETE` | `/api/v1/books/{id}`   | Delete a book                         |

### Examples

```shell
# Create
curl -X POST http://localhost:8080/api/v1/books \
  -H 'Content-Type: application/json' \
  -d '{"title":"Vert.x 5 in Action"}'
# {"id": 1, "title": "Vert.x 5 in Action"}

# List (paginated)
curl 'http://localhost:8080/api/v1/books?page=1&limit=5'
# {"total": 1, "limit": 5, "page": 1, "books": [{"id": 1, "title": "Vert.x 5 in Action"}]}

# Get by id
curl http://localhost:8080/api/v1/books/1

# Update
curl -X PUT http://localhost:8080/api/v1/books/1 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Updated title"}'

# Delete (204 No Content)
curl -X DELETE http://localhost:8080/api/v1/books/1
```

Errors are returned as JSON, e.g. `404` → `{"error": "No book with id 1"}`.

## Docker

The `Dockerfile` is a multi-stage build: it compiles the project with `maven:3.9-eclipse-temurin-25`
and runs the fat jar on `eclipse-temurin:25-jre-alpine` as a non-root user.

```shell
docker build -t vertx-rest-api-sample .
```

> **Note:** the application reads its database settings from `application.properties`
> (default host `localhost`). When running the app inside a container, adjust
> `datasource.host` to point at a reachable PostgreSQL host (e.g. `db` on the Compose network).

## CI/CD

GitHub Actions ([`.github/workflows/maven.yml`](.github/workflows/maven.yml)) runs on every push and pull request to `main`:

- **Build & Test** on JDK 25 (Temurin) with Maven caching
- **Docker image build** to validate the `Dockerfile`
- **Dependency graph submission** (push to `main` only) to improve Dependabot alerts — non-blocking,
  requires *Settings → Code security → Dependency graph* to be enabled

## Dependency Updates

[Dependabot](.github/dependabot.yml) keeps dependencies up to date:

| Ecosystem       | Schedule                    | Notes |
|-----------------|-----------------------------|-------|
| Maven           | Daily, 05:00 Asia/Jakarta   | Minor/patch updates grouped (Vert.x, Jackson, database, Netty, test, Maven plugins, others); majors arrive as separate PRs |
| GitHub Actions  | Weekly (Monday)             | All actions grouped |
| Docker          | Weekly (Monday)             | Stays on the JDK 25 LTS line |
| Docker Compose  | Weekly (Monday)             | PostgreSQL image updates |

## Help

- [Vert.x Documentation](https://vertx.io/docs/)
- [Vert.x 5 migration guide](https://vertx.io/docs/guides/vertx-5-migration-guide/)
- [Vert.x on Stack Overflow](https://stackoverflow.com/questions/tagged/vert.x?sort=newest&pageSize=15)
- [Vert.x Discord](https://discord.gg/6ry7aqPWXy)

## Author

**Hendi Santika** — [s.id/hendisantika](https://s.id/hendisantika) · Telegram [@hendisantika34](https://t.me/hendisantika34)
