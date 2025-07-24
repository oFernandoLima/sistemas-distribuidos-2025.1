# Trabalho 3: Sistema de Correios

## 👥 Equipe

* **Luis Fernando Batista Lima** - 538134
* **Pedro Henrique Santos Moreira** - 536925
* **Curso:** Engenharia de Software

## Resumo

Este relatório descreve a implementação de um sistema de correios distribuído utilizando Web Services (API REST) com FastAPI em Python e clientes desenvolvidos em JavaScript (Node.js) e Java. O sistema permite o gerenciamento de correspondências (cartas, encomendas e telegramas) através de uma arquitetura cliente-servidor baseada em requisições HTTP.

## 1. Arquitetura do Sistema

### 1.1 Visão Geral
O sistema utiliza uma arquitetura cliente-servidor com os seguintes componentes:

- **Servidor (API)**: Implementado em Python usando FastAPI
- **Cliente JavaScript**: Implementado em Node.js usando Axios para requisições HTTP
- **Cliente Java**: Implementado usando Apache HttpClient e Jackson para JSON

### 1.2 Protocolo de Comunicação
- **Protocolo**: HTTP/HTTPS
- **Formato de Dados**: JSON
- **Métodos HTTP**: GET, POST, DELETE
- **Porta**: 8000 (configurável)

## 2. Implementação da API (Servidor)

### 2.1 Tecnologias Utilizadas
- **FastAPI**: Framework web moderno para Python
- **Pydantic**: Validação de dados e serialização
- **Uvicorn**: Servidor ASGI para execução da aplicação

### 2.2 Endpoints Implementados

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/loja/nome` | Retorna o nome da loja |
| POST | `/correspondencias/carta` | Registra uma nova carta |
| POST | `/correspondencias/encomenda` | Registra uma nova encomenda |
| POST | `/correspondencias/telegrama` | Registra um novo telegrama |
| GET | `/correspondencias` | Lista todas as correspondências |
| GET | `/correspondencias/{codigo}` | Consulta correspondência por código |
| GET | `/correspondencias/{codigo}/preco` | Consulta preço por código |
| DELETE | `/correspondencias/{codigo}` | Remove correspondência (entrega) |

### 2.3 Modelos de Dados

**Carta**
```python
class Carta(BaseModel):
    codigo: str
    destinatario: str
    endereco: str
    selada: bool
```

**Encomenda**
```python
class Encomenda(BaseModel):
    codigo: str
    destinatario: str
    endereco: str
    peso: float
```

**Telegrama**
```python
class Telegrama(BaseModel):
    codigo: str
    destinatario: str
    endereco: str
    numero_palavras: int
```

### 2.4 Cálculo de Preços
- **Carta**: R$ 2,00 (selada) ou R$ 1,50 (não selada)
- **Encomenda**: R$ 5,00 por kg
- **Telegrama**: R$ 0,50 por palavra

## 3. Implementação dos Clientes

### 3.1 Cliente JavaScript (Node.js)

**Tecnologias:**
- Node.js como runtime
- Axios para requisições HTTP
- ReadlineSync para interface de usuário

**Características:**
- Interface interativa via terminal
- Tratamento de erros robusto
- Formatação de dados para exibição

### 3.2 Cliente Java

**Tecnologias:**
- Java 11+ com Maven
- Apache HttpClient para requisições HTTP
- Jackson para processamento JSON
- Scanner para entrada do usuário

**Características:**
- Orientação a objetos com modelos separados
- Gerenciamento automático de recursos
- Interface de usuário intuitiva

## 4. Funcionalidades Implementadas

### 4.1 Registrar Correspondência
Permite adicionar cartas, encomendas ou telegramas ao sistema com validação de dados e cálculo automático de preços.

### 4.2 Listar Correspondências
Exibe todas as correspondências cadastradas com informações detalhadas incluindo preços calculados.

### 4.3 Consultar Preço
Permite consultar o preço de uma correspondência específica através do código.

### 4.4 Entregar Correspondência
Remove a correspondência do sistema, simulando a entrega.

### 4.5 Obter Nome da Loja
Retorna informações básicas sobre o serviço.

## 5. Testando a Interoperabilidade

### 5.1 Teste Multi-Cliente
O sistema foi testado com múltiplos clientes acessando simultaneamente:
1. Cliente JavaScript registra uma carta
2. Cliente Java lista correspondências (visualiza a carta)
3. Cliente Java consulta preço da carta
4. Cliente JavaScript entrega a correspondência

### 5.2 Validação de Dados
Todos os clientes implementam validação básica e tratamento de erros da API.

## 6. Vantagens da Arquitetura Escolhida

### 6.1 Web Services REST
- **Simplicidade**: HTTP é um protocolo amplamente conhecido
- **Interoperabilidade**: Funciona com qualquer linguagem que suporte HTTP
- **Escalabilidade**: Stateless por natureza
- **Documentação**: FastAPI gera documentação automática (Swagger)

### 6.2 Separação de Responsabilidades
- Servidor centraliza a lógica de negócio
- Clientes focam na interface do usuário
- Fácil manutenção e evolução independente

## 7. Desafios e Soluções

### 7.1 Sincronização de Estado
**Desafio**: Manter consistência entre múltiplos clientes
**Solução**: Armazenamento centralizado no servidor

### 7.2 Tratamento de Erros
**Desafio**: Diferentes formas de tratamento em cada linguagem
**Solução**: Padronização de respostas de erro na API

### 7.3 Validação de Dados
**Desafio**: Garantir integridade dos dados
**Solução**: Validação tanto no cliente quanto no servidor

## 8. Conclusão

O sistema implementado demonstra com sucesso a utilização de Web Services para comunicação entre aplicações distribuídas. A escolha do FastAPI para o servidor proporcionou desenvolvimento rápido e documentação automática, enquanto os clientes em diferentes linguagens mostram a interoperabilidade da solução.

A arquitetura REST se mostrou adequada para o domínio do problema, oferecendo simplicidade e flexibilidade. O sistema atende todos os requisitos especificados e pode ser facilmente estendido para incluir novas funcionalidades.

## 9. Possíveis Melhorias

- Implementação de autenticação e autorização
- Persistência em banco de dados
- Cache para melhorar performance
- Logging mais detalhado
- Testes automatizados
- Deploy em containers (Docker)

## 10. Referências

- FastAPI Documentation: https://fastapi.tiangolo.com/
- HTTP Status Codes: https://httpstatuses.com/
- REST API Best Practices: https://restfulapi.net/
- Jackson JSON Documentation: https://github.com/FasterXML/jackson
- Axios Documentation: https://axios-http.com/
