# StudyMatch Core

StudyMatch Core, öğrencilerin çalışma arkadaşları ve grupları bulmasını sağlayan bir backend projesidir. Spring Boot ve Clean Architecture prensipleri kullanılarak geliştirilmiştir.

## Teknoloji Yığını

-   **Java 21**
-   **Spring Boot 3.x** (Web, Data JPA, Security, Validation)
-   **PostgreSQL** (Veritabanı)
-   **Flyway** (Veritabanı Migrasyonu)
-   **JJWT** (JWT Authentication)
-   **SpringDoc OpenAPI** (API Dokümantasyonu)

## Kurulum ve Çalıştırma

### Gereksinimler

-   JDK 21
-   Docker (PostgreSQL için)

### Adımlar

1.  Projeyi klonlayın:
    ```bash
    git clone https://github.com/engnhn/studymatch-core.git
    cd studymatch-core
    ```

2.  Veritabanını başlatın (Docker varsa):
    ```bash
    docker run --name studymatch-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=studymatch -p 5432:5432 -d postgres
    ```

3.  Uygulamayı derleyin ve çalıştırın:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```

4.  API Dokümantasyonuna erişin:
    -   Swagger UI: `http://localhost:8080/swagger-ui.html`

## Testler

Testleri çalıştırmak için:
```bash
./mvnw test
```
