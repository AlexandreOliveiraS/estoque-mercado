🧾 Controle de Estoque — Java 21 + Spring Boot 3

Aplicação completa de controle de estoque com Java 21 e Spring Boot 3.3.
Front server-side com Thymeleaf, APIs REST documentadas no Swagger, perfis para DEV (H2) e PROD (Postgres em Docker), segurança básica, integração mock de código de barras, testes e passo-a-passo para rodar.

Login: admin / admin123
UI: http://localhost:8080/products
Swagger: http://localhost:8080/swagger-ui/index.html

✨ Funcionalidades

Cadastro de produtos (nome, código de barras, preço, quantidade, mínimo)

Entrada e Saída de estoque (ajuste incremental)

Alerta visual para estoque mínimo

Integração mock por código de barras (/api/barcode/{barcode})

Documentação viva com Swagger UI

Perfis: dev (H2) e prod (Postgres/Docker)

🧰 Stack

Java 21 · Spring Boot 3.3 · Spring MVC · Spring Data JPA (Hibernate) · Spring Security · Thymeleaf · H2 (dev) · PostgreSQL (prod) · springdoc-openapi · Maven

🧭 Endpoints principais
Método	Caminho	Descrição
GET	/api/products	Lista produtos (paginado)
POST	/api/products	Cria produto
POST	/api/products/{id}/entrada?qtd=N	Entrada de estoque
POST	/api/products/{id}/saida?qtd=N	Saída de estoque
GET	/api/barcode/{barcode}	Busca mock por código barras

Exemplo de criação:

curl -X POST "http://localhost:8080/api/products" \
  -H "Content-Type: application/json" \
  -d '{
        "name": "Arroz 5kg",
        "barcode": "7891234567890",
        "quantity": 30,
        "minQuantity": 10,
        "price": 19.90
      }'

🚀 Como rodar
1) DEV (H2 em memória)
mvn spring-boot:run -Dspring-boot.run.profiles=dev


UI: http://localhost:8080/products

Swagger: http://localhost:8080/swagger-ui/index.html

H2: http://localhost:8080/h2

Dev é rápido para iterar: H2 em memória, ddl-auto=update, cache Thymeleaf desativado.

2) PROD (Postgres via Docker)

Suba o banco:

docker compose up -d
docker compose ps   # db deve estar healthy


Rode a app:

mvn spring-boot:run -D"spring-boot.run.profiles=prod"


Conexão:

jdbc:postgresql://localhost:5432/estoque
user: postgres  |  password: postgres

3) Build/JAR
mvn -DskipTests clean package
java -jar target/estoque-mercado-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

4) Testes
mvn test

⚙️ Perfis & propriedades (resumo)

application-dev.properties

H2 in-memory (jdbc:h2:mem:mercado;DB_CLOSE_DELAY=-1)

spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true

Thymeleaf cache off

application-prod.properties

Postgres (jdbc:postgresql://localhost:5432/estoque)

spring.jpa.hibernate.ddl-auto=update

Thymeleaf cache on

Integração de código de barras: mock ativado com @Profile({"dev","prod"}).

