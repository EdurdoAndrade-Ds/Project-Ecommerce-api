# Project Ecommerce API

![Java](https://img.shields.io/badge/Java-17-blue)
![SpringBoot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

Microserviço de E-commerce desenvolvido com Java 17 e Spring Boot, seguindo boas práticas de arquitetura e segurança. O projeto é responsável por realizar a gestão de produtos, pedidos e autenticação com usuários e clientes.

🔗 Acesso público à API Swagger:  
👉 [Swagger UI](https://project-ecommerce-api.onrender.com/swagger-ui/index.html)

---

## 🛠 Pré-requisitos

Antes de começar, você precisa ter instalado:

- Java 17+
- Maven 3.8+
- PostgreSQL

💡 Recomenda-se o uso de uma IDE como: IntelliJ, VS Code ou Eclipse

---

## 🧩 Visão geral

Este projeto simula uma plataforma robusta e escalável para o gerenciamento de um sistema de e-commerce, com foco em boas práticas de arquitetura e autenticação JWT.

### Destaques e práticas aplicadas:

- 🧩 Arquitetura em camadas baseada no padrão MVC (Model-View-Controller)
- 🧠 Organização orientada a DDD (Domain-Driven Design)
- 🔐 Autenticação segura com JWT
- ✅ Validação de dados com Bean Validation
- 📦 Uso de DTOs (Data Transfer Objects)
- 📚 Documentação interativa com Swagger
- 🛢️ Integração com PostgreSQL via Spring Data JPA
- 🐳 Suporte a Docker
- ⛔ Tratamento centralizado de exceções

---

## ⚙️ Funcionalidades atuais

✅ Cadastro, autenticação e gerenciamento de clientes  
✅ Cadastro, listagem, detalhamento, atualização e exclusão de produtos  
✅ Criação e cancelamento de pedidos
✅ Listagem de pedidos por cliente
✅ Pagamento de pedidos
✅ Proteção de rotas com JWT
✅ Documentação automática com Swagger

---

## 🗂 Estrutura do Projeto

```
ecommerce-api/
├── modules/
│   ├── cliente/
│   │   ├── dtos/
│   │   ├── controllers/
│   │   ├── services/
│   │   ├── repositories/
│   │   └── entities/
│   ├── produto/
│   │   ├── dtos/
│   │   ├── controllers/
│   │   ├── services/
│   │   ├── repositories/
│   │   └── entities/
│   ├── pedido/
│       ├── dtos/
│       ├── controllers/
│       ├── services/
│       ├── repositories/
│       └── entities/
│   └── payment/
│       ├── dto/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       └── entity/
├── security/
├── providers/
├── exceptions/
└── ...
```

---

## 📦 Exemplos de Payloads

### 🧑 Cadastro de Cliente
```json
{
  "nome": "João Cliente",
  "email": "joao@email.com",
  "senha": "123456"
}
```

### 🛍️ Criação de Produto
```json
{
  "nome": "Teclado Mecânico",
  "descricao": "Teclado com switches azuis e iluminação RGB",
  "preco": 350.00,
  "quantidade": 10
}
```

### 🛒 Criação de Pedido
```json
{
  "produtosIds": [1, 2, 3]
}
```

---

## 🚀 Como executar o projeto

📥 Passo 1 – Clone o repositório
```bash
git clone https://github.com/EdurdoAndrade-Ds/Project-Ecommerce-api.git
cd Project-Ecommerce-api
```

🧾 Passo 2 – Configure o banco de dados PostgreSQL  
No arquivo `src/main/resources/application.properties`, configure:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

🚀 Passo 3 – Execute o projeto
```bash
./mvnw spring-boot:run
```

🐳 Alternativa com Docker
```bash
./mvnw clean package -DskipTests
docker-compose up --build
```

## 🧪 Como executar os testes

Para rodar todos os testes automatizados e gerar o relatório de cobertura:

```bash
./mvnw clean verify
```

### Execução offline

Se o ambiente não possuir acesso à internet, certifique-se de ter o cache local do Maven previamente populado e utilize o modo offline:

```bash
./mvnw -o clean verify
```

O relatório do JaCoCo será gerado em `target/site/jacoco/index.html`.

---

## 👨‍💻 Autores

#### Eduardo Andrade
- Cadastro e autenticação de cliente
- Criação e gerenciamento de produtos
- Criação e listagem de pedidos
- Autorização com JWT
- Documentação com Swagger
- Deploy com Docker e Render
- Tratamento global de exceções
- Ajuste de Bugs

#### Jose Guilherme
- Testes unitários com JUnit e Mockito de Cliente e Pedido
- Implementacao de metodos do modulo cliente
- Refatoracao do modulo cliente

#### Maria Eduarda
- dockerfile

#### Mateus
- Testes unitários com JUnit e Mockito de Produto
- Implementacao da logica do modulo Produto
- Refatoracao do modulo Produto

#### Pedro Costa
- Implementacao do criar Pedido
- Finalizacao da logica DTOs

#### Izabela
- Implementacao do dto Pedido
- Refatoracao do Pedido

---

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

> Projeto desenvolvido com fins educacionais como parte de um sistema completo de e-commerce em Java com Spring Boot.
