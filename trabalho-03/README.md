# Sistema de Correios - Trabalho 3

Este projeto implementa um sistema de correios usando arquitetura cliente-servidor com Web Services (API REST).

## Estrutura do Projeto

```
trabalho-03/
├── api-python/          # API FastAPI em Python
├── cliente-javascript/  # Cliente em JavaScript (Node.js)
├── cliente-java/       # Cliente em Java (Maven)
└── README.md
```

## Componentes

### 1. API Python (FastAPI)
- **Localização**: `api-python/`
- **Tecnologia**: FastAPI + Uvicorn
- **Porta**: 8000
- **Funcionalidades**:
  - Registrar correspondências (carta, encomenda, telegrama)
  - Listar todas as correspondências
  - Consultar preço por código
  - Entregar correspondência (remover do sistema)
  - Obter nome da loja

### 2. Cliente JavaScript
- **Localização**: `cliente-javascript/`
- **Tecnologia**: Node.js + Axios + ReadlineSync
- **Funcionalidades**: Interface interativa via terminal

### 3. Cliente Java
- **Localização**: `cliente-java/`
- **Tecnologia**: Java 11 + Maven + HttpClient + Jackson
- **Funcionalidades**: Interface interativa via terminal

## Como Executar

### 1. Executar a API (Python)

```bash
cd api-python
pip install -r requirements.txt
python main.py
```

A API estará disponível em: http://localhost:8000

### 2. Executar Cliente JavaScript

```bash
cd cliente-javascript
npm install
npm start
```

### 3. Executar Cliente Java

```bash
cd cliente-java
mvn compile
mvn exec:java
```

## Endpoints da API

- `GET /` - Página inicial
- `GET /loja/nome` - Obter nome da loja
- `POST /correspondencias/carta` - Registrar carta
- `POST /correspondencias/encomenda` - Registrar encomenda
- `POST /correspondencias/telegrama` - Registrar telegrama
- `GET /correspondencias` - Listar correspondências
- `GET /correspondencias/{codigo}` - Consultar correspondência
- `GET /correspondencias/{codigo}/preco` - Consultar preço
- `DELETE /correspondencias/{codigo}` - Entregar correspondência

## Modelos de Dados

### Carta
- código: string
- destinatário: string
- endereço: string
- selada: boolean
- Preço: R$ 2,00 (selada) ou R$ 1,50 (não selada)

### Encomenda
- código: string
- destinatário: string
- endereço: string
- peso: float (kg)
- Preço: R$ 5,00 por kg

### Telegrama
- código: string
- destinatário: string
- endereço: string
- número de palavras: integer
- Preço: R$ 0,50 por palavra

## Documentação da API

Com a API rodando, acesse: http://localhost:8000/docs para ver a documentação interativa do Swagger.

## Testando o Sistema

1. Inicie a API Python
2. Execute um dos clientes (JavaScript ou Java)
3. Use o menu interativo para testar as funcionalidades
4. Teste com ambos os clientes simultaneamente para verificar a sincronização

## Requisitos

### Python
- Python 3.8+
- FastAPI
- Uvicorn
- Pydantic

### JavaScript
- Node.js 14+
- Axios
- ReadlineSync

### Java
- Java 11+
- Maven 3.6+
- Apache HttpClient
- Jackson

## 👥 Equipe

* **Luis Fernando Batista Lima** - 538134
* **Pedro Henrique Santos Moreira** - 536925
* **Curso:** Engenharia de Software