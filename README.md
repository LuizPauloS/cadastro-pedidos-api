# Cadastro Pedidos API

A aplicação **Cadastro Pedidos API** é uma aplicação para gerenciamento de pedidos de produtos ou serviços.

# Tecnologias

Para criar o sistema foram utilizados as seguintes ferramentas/frameworks:

- Backend com Spring-boot v2.2+ e Java v8

- Banco de dados utilizando o PostgreSQL

# Composição da Stack

A Stack do Cadastro Pedidos API é composta por 1 aplicação (apenas backend), que contém a conexão com o banco de dados, 
e gerenciamento das regras de negócio do sistema.

## O que preciso para subir a aplicação

- Gerenciador de dependencias e build, Maven.

- Java 8.

## Gerando o Pacote
Sendo um projeto Maven, execute os goals clean e install na raiz do projeto para baixar as dependências e gerar jar do projeto

     #!/cadastro-pedidos-api
     $ mvn clean install
     
## Executando o Jar

Como se trata de um projeto Spring Boot, podemos simplismente executar o jar que foi gerado na pasta target e a 
aplicação irá subir em um tomcat embedded.

    #!/cadastro-pedidos-api/target
    $ java -jar cadastro-pedidos-api-0.0.1-SNAPSHOT.jar

Configuração da porta da api se encontra no application.yml:
		
	server:
	    port: 9000

Pronto, a aplicação deve estar online na porta 9000.

## Consumir API

Para consumir a API basta acessar por exemplo a url para buscar todos os pedidos http://localhost:9000/cadastro-pedidos-api/v1/pedidos.

