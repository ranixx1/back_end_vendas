# Sistema de Estoque e Vendas com Spring Boot (back-end)

Este é um projeto de um sistema básico de estoque e vendas desenvolvido com Spring Boot, utilizando uma arquitetura em camadas (Controller, Service, Repository, Model/Entity, DTO) e um banco de dados H2 para persistência.

## Funcionalidades Implementadas

- **CRUD (Criar, Ler, Atualizar, Deletar) de Produtos:**
  - Cadastro e gestão de itens do estoque.
- **CRUD (Criar, Ler, Atualizar, Deletar) de Clientes:**
  - Registro e gestão de informações de clientes.
- **Registro de Vendas:**
  - Criação de novas vendas, incluindo múltiplos itens (produtos).
  - Atualização automática do estoque dos produtos após a venda.
- **Cancelamento de Vendas:**
  - Permite cancelar uma venda existente, revertendo automaticamente o estoque dos produtos vendidos.
- **Tratamento de Erros Personalizado:**
  - Lida com exceções específicas (e.g., recursos não encontrados, requisições inválidas, estoque insuficiente) retornando respostas HTTP apropriadas (404 Not Found, 400 Bad Request) e mensagens claras.
- **Validação de Entrada de Dados:**
  - Utiliza Bean Validation para garantir a integridade dos dados recebidos nas requisições da API (e.g., quantidades positivas, lista de itens não vazia).
- **Logging Básico:**
  - Implementação de logs informativos em diferentes níveis (INFO, DEBUG, WARN, ERROR) para auxiliar na depuração, monitoramento e compreensão do fluxo da aplicação.
- **Persistência de Dados:**
  - Utiliza H2 Database em modo de arquivo para armazenar dados localmente.

## Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x:** Framework para desenvolvimento de aplicações Java.
- **Spring Web:** Para construir APIs RESTful.
- **Spring Data JPA:** Para simplificar a interação com o banco de dados.
- **Spring Boot Starter Validation:** Para validação de dados via Bean Validation.
- **SLF4J & Logback:** Para gerenciamento de logs.
- **H2 Database:** Banco de dados em memória/arquivo, ideal para desenvolvimento.
- **Lombok:** Para reduzir código boilerplate (getters, setters, construtores).
- **Maven:** Ferramenta de automação de build e gerenciamento de dependências.

## Como Rodar o Projeto Localmente

### Pré-requisitos

- Java Development Kit (JDK) 17 ou superior.
- Maven (geralmente incluído nas IDEs ou pode ser instalado separadamente).
- Sua IDE favorita (IntelliJ IDEA, VS Code com Extension Pack for Java, Eclipse).

### Passos para Configuração e Execução

1.  **Clone o repositório:**
    ```bash
    git clone [https:[github.com/rannix1/estoque_vendas](https://github.com/rannix1/estoque_vendas)]
    cd estoque-vendas
    ```
2.  **Abra o projeto na sua IDE:**
    - No VS Code, vá em `File > Open Folder...` e selecione a pasta `estoque-vendas`. A IDE deve reconhecer o projeto Maven e baixar as dependências.
3.  **Configuração do Banco de Dados H2:**
    - O projeto utiliza o H2 Database em modo de arquivo. As configurações estão em `src/main/resources/application.properties`.
    - O banco de dados será criado automaticamente no caminho `./data/estoque_db.mv.db` (dentro da pasta `data` na raiz do projeto) na primeira execução.
4.  **Execute a Aplicação:**
    - Navegue até a classe principal `EstoqueVendasApplication.java` (localizada em `src/main/java/com/example/estoque_vendas/`).
    - Execute o método `main` (clique no botão "Run" na sua IDE).
    - A aplicação estará disponível em `http://localhost:8080`.

## Testando a API

Você pode usar ferramentas como [Postman](https://www.postman.com/), [Insomnia](https://insomnia.rest/), ou a extensão [REST Client para VS Code](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) para testar os endpoints.

### Endpoints Principais:

- **Produtos:**
  - `GET /api/produtos`: Listar todos os produtos.
  - `GET /api/produtos/{id}`: Obter produto por ID.
  - `POST /api/produtos`: Criar novo produto.
    ```json
    {
        "nome": "Exemplo Produto",
        "descricao": "Descrição do produto",
        "quantidadeEstoque": 10,
        "precoCusto": 5.00,
        "precoVenda": 10.00
    }
    ```
  - `PUT /api/produtos/{id}`: Atualizar produto.
  - `DELETE /api/produtos/{id}`: Deletar produto.

- **Clientes:**
  - `GET /api/clientes`: Listar todos os clientes.
  - `GET /api/clientes/{id}`: Obter cliente por ID.
  - `POST /api/clientes`: Criar novo cliente.
    ```json
    {
        "nome": "Nome do Cliente",
        "documento": "12345678900",
        "email": "cliente@email.com",
        "telefone": "99999999999"
    }
    ```
  - `PUT /api/clientes/{id}`: Atualizar cliente.
  - `DELETE /api/clientes/{id}`: Deletar cliente.

- **Vendas:**
  - `GET /api/vendas`: Listar todas as vendas.
  - `GET /api/vendas/{id}`: Obter venda por ID.
  - `POST /api/vendas`: Registrar uma nova venda.
    ```json
    {
        "clienteId": 1,
        "itens": [
            {
                "produtoId": 1,
                "quantidade": 1
            }
        ]
    }
    ```
  - `DELETE /api/vendas/{id}`: Cancelar uma venda existente, estornando o estoque.

### Console do H2 Database:

- Acesse o console do H2 em `http://localhost:8080/h2-console`.
- **JDBC URL:** `jdbc:h2:file:./data/estoque_db`
- **User Name:** `sa`
- **Password:** (em branco)

---

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.
