# Project Ecommerce API

Microserviço de E-commerce desenvolvido com Java 17 e Spring Boot, seguindo boas práticas de arquitetura e segurança. O projeto é responsável por realizar a gestão de produtos, pedidos, pagamentos e autenticação com usuários e clientes.

🔗 Acesso público à API Swagger:  
https://project-ecommerce-api.onrender.com/swagger-ui/index.html

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
✅ Cadastro e listagem de produtos  
✅ Criação e cancelamento de pedidos  
✅ Pagamento de pedidos  
✅ Listagem de pedidos por cliente  
✅ Proteção de rotas com JWT  
✅ Documentação automática com Swagger

---

## 📦 Tecnologias utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- Hibernate Validator

---

## 🗂 Estrutura do Projeto

```
ecommerce-api/
├── modules/
│   ├── cliente/
│   ├── produto/
│   ├── pedido/
│   └── pagamento/
├── security/
├── providers/
├── dtos/
├── controllers/
├── services/
├── repositories/
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

### 🛒 Criação de Pedido
```json
{
  "produtosIds": [1, 2, 3]
}
```

### 💳 Pagamento
```json
{
  "pedidoId": 5,
  "tipoPagamento": "CARTAO_CREDITO"
}
```

---

## 🚀 Como executar o projeto

📥 Passo 1 – Clone o repositório
```bash
git clone https://github.com/seuusuario/project-ecommerce-api.git
cd project-ecommerce-api
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

---

## 👨‍💻 Autores

Eduardo Andrade  
🔹 Cadastro e autenticação de cliente  
🔹 Cadastro e gerenciamento de produtos  
🔹 Criação e listagem de pedidos  
🔹 Pagamentos e histórico do cliente  
🔹 Autorização com JWT  
🔹 Documentação com Swagger  
🔹 Deploy com Docker e Render  
🔹 Testes unitários com JUnit e Mockito  
🔹 Tratamento global de exceções

---

> Projeto desenvolvido com fins educacionais como parte de um sistema completo de e-commerce em Java com Spring Boot.
