# Sistema de Cartões

Este é o meu primeiro projeto utilizando Docker, desenvolvido para fins educativos. O projeto é um sistema de cartões que incorpora diversos conceitos e tecnologias, incluindo Docker, Spring Cloud, RabbitMQ, Keycloak e Spring Eureka. Planejamos lançar uma nova versão com mais refinamentos e a inclusão de documentação com Swagger.

## Tecnologias Utilizadas

- **Docker**: Para containerização e gerenciamento de aplicações.
- **Spring Cloud**: Para criar uma arquitetura de microsserviços.
- **RabbitMQ**: Para mensageria e comunicação entre serviços.
- **Keycloak**: Para autenticação e autorização.
- **Spring Eureka**: Para descoberta de serviços.
- **Swagger** (planejado): Para documentação da API.

## Funcionalidades

- **Gerenciamento de Cartões**: Criação, atualização e exclusão de cartões.
- **Autenticação e Autorização**: Utilização do Keycloak para segurança.
- **Mensageria**: RabbitMQ para comunicação entre diferentes partes do sistema.
- **Descoberta de Serviços**: Spring Eureka para registro e descoberta de serviços.

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

- `service-card`: Serviço responsável pelo gerenciamento de cartões.
- `service-auth`: Serviço responsável pela autenticação e autorização.
- `service-messaging`: Serviço responsável pela comunicação via RabbitMQ.
- `service-discovery`: Serviço de descoberta utilizando Spring Eureka.

## Pré-requisitos

- Docker e Docker Compose instalados.
- Java 17 ou superior.
- Maven.

## Como Executar

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/sistema-de-cartoes.git
    cd sistema-de-cartoes
    ```

2. Construa os serviços:
    ```bash
    mvn clean install
    ```

3. Inicie os contêineres Docker:
    ```bash
    docker-compose up
    ```

4. Acesse o sistema:
    - Eureka Dashboard: `http://localhost:8761`
    - Keycloak: `http://localhost:8080/auth`
    - Swagger (planejado): `http://localhost:8080/swagger-ui.html`

## Próximos Passos

- **Documentação da API**: Integração com Swagger para documentação das APIs.
- **Testes**: Implementação de testes automatizados para garantir a qualidade do código.
- **Refinamentos**: Melhoria da arquitetura e desempenho do sistema.

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.
