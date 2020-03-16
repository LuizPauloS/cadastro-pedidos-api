# Cadastro Pedidos API (Nível III)

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

- PostgreSQL


## Configurações do Banco de Dados:

Configurações do banco de dados se encontram no application.yml conforme abaixo:

    spring:
        datasource:
            driver-class-name: org.postgresql.Driver
            url: jdbc:postgresql://localhost:5432/cadastro_pedidos?createDatabaseIfNotExist=true
            username: postgres
            password: postgres
    
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

### Exemplos:

#### Create Produtos/Serviços:

Entrada 1:

http://localhost:9000/cadastro-pedidos-api/v1/produtos-servicos

    {
        "nome": "Creme Dental",
        "descricao": "Creme Dental XPTO.",
        "categoria": "PRODUTO",
        "precoUnitario": 1.90,
        "quantidadeEstoque": 10
    }
    
Saída 1: 200 OK

    {
        "id": "75a467db-3b15-493d-8715-8e5ef9e44619",
        "nome": "Creme Dental",
        "descricao": "Creme Dental XPTO.",
        "categoria": "PRODUTO",
        "precoUnitario": 1.9,
        "quantidadeEstoque": 10,
        "ativo": true
    }
    
Entrada 2:

    {
        "nome": "Palestra Motivacional",
        "descricao": "Palestra Motivacional para empresas.",
        "categoria": "SERVICO",
        "precoUnitario": 500,
        "quantidadeEstoque": 0
    }
    
Saída 2:

    {
        "id": "aa2cd9bb-d00f-4a5a-925b-6b265357ad44",
        "nome": "Palestra Motivacional",
        "descricao": "Palestra Motivacional para empresas.",
        "categoria": "SERVICO",
        "precoUnitario": 500,
        "quantidadeEstoque": 0,
        "ativo": true
    }
    
#### Create Pedidos / Itens Pedidos:

Entrada:

http://localhost:9000/cadastro-pedidos-api/v1/pedidos

    {
    	"pedidoAberto": true,
    	"percentualDesconto": 1.0,
    	"itensPedido": [
    		{
    			"produtoServico": {
    	            "id": "75a467db-3b15-493d-8715-8e5ef9e44619",
    		        "nome": "Creme Dental",
    		        "descricao": "Creme Dental XPTO.",
    		        "categoria": "PRODUTO",
    		        "precoUnitario": 1.9,
    		        "quantidadeEstoque": 10,
    		        "ativo": true
            	},
            	"quantidadeItem": 2
    		},
    		{
    			"produtoServico": {
    			    "id": "aa2cd9bb-d00f-4a5a-925b-6b265357ad44",
    			    "nome": "Palestra Motivacional",
    			    "descricao": "Palestra Motivacional para empresas.",
    			    "categoria": "SERVICO",
    			    "precoUnitario": 500,
    			    "quantidadeEstoque": 0,
    			    "ativo": true
    
            	},
            	"quantidadeItem": 1
    		}
    	]
    }
    
Saída: OK

    {
        "id": "3052be6d-8ed9-4ada-8d50-8a8383790397",
        "valorTotal": 503.76,
        "pedidoAberto": true,
        "dataPedido": "2020-03-16T16:40:25.616021",
        "itensPedido": [
            {
                "id": "b6709147-b5b5-4835-9f6b-ab5a2adc2a78",
                "produtoServico": {
                    "id": "75a467db-3b15-493d-8715-8e5ef9e44619",
                    "nome": "Creme Dental",
                    "descricao": "Creme Dental XPTO.",
                    "categoria": "PRODUTO",
                    "precoUnitario": 1.9,
                    "quantidadeEstoque": 10,
                    "ativo": true
                },
                "quantidadeItem": 2
            },
            {
                "id": "d38a3d01-c096-4036-a5ec-2b336f8398ef",
                "produtoServico": {
                    "id": "aa2cd9bb-d00f-4a5a-925b-6b265357ad44",
                    "nome": "Palestra Motivacional",
                    "descricao": "Palestra Motivacional para empresas.",
                    "categoria": "SERVICO",
                    "precoUnitario": 500,
                    "quantidadeEstoque": 0,
                    "ativo": true
                },
                "quantidadeItem": 1
            }
        ],
        "percentualDesconto": 1
    }