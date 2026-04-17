# ⚙️ Primeira Casa - Backend API

Esta é a API central do ecossistema **Primeira Casa**, responsável por gerenciar toda a lógica de negócio, autenticação e persistência de dados. O projeto foi construído seguindo as melhores práticas de desenvolvimento com Java e Spring Boot.

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 17+
* **Framework:** Spring Boot 3
* **Segurança:** Spring Security com autenticação via **JWT (JSON Web Token)**
* **Banco de Dados:** Spring Data JPA / Hibernate
* **Infraestrutura:** Docker (Dockerfile incluso para fácil deploy)
* **Gerenciamento de Dependências:** Maven

## ✨ Funcionalidades e Diferenciais Técnicos

* **Segurança Robusta:** Implementação de filtros de segurança personalizados para validação de tokens JWT em cada requisição.
* **Arquitetura de Dados:** Uso de **DTOs (Data Transfer Objects)** para separar as entidades de banco de dados das respostas da API, garantindo segurança e performance.
* **Filtros Avançados:** Implementação de **Spring Specifications** para permitir buscas dinâmicas e complexas de itens.
* **Tratamento de Erros:** Global Exception Handler para respostas de erro padronizadas e amigáveis ao frontend.
* **CORS Configurado:** Pronto para integração com o frontend em diferentes domínios.

## 🏗️ Estrutura do Projeto

O código está organizado em camadas para facilitar a manutenção e escalabilidade:
* `Controller`: Endpoints REST para comunicação com o frontend.
* `Service`: Camada de lógica de negócio e regras da aplicação.
* `Repository`: Interfaces para comunicação com o banco de dados.
* `Model`: Entidades que representam o domínio do projeto (Produto, Lista, ItemCasa).

## 🛠️ Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/nathanta2001/primeiracasa-backend.git](https://github.com/nathanta2001/primeiracasa-backend.git)
    ```

2.  **Configure as variáveis de ambiente:**
    Renomeie o arquivo `.env.example` para `.env` e preencha as credenciais do banco de dados e a secret do JWT.

3.  **Execute com Maven:**
    ```bash
    ./mvnw spring-boot:run
    ```

4.  **Ou via Docker:**
    ```bash
    docker build -t primeiracasa-backend .
    docker run -p 8080:8080 primeiracasa-backend
    ```

---
Desenvolvido por [Nathan](https://github.com/nathanta2001) 🚀
