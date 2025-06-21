# 📦 Sistema de Controle de Volumes dos Correios

**Sistemas Distribuídos com Middleware UDP** — UFC Campus Quixadá

## 🧾 Descrição

Sistema completo de controle de correspondências dos Correios implementado com **arquitetura Middleware UDP**:
- **Comunicação:** UDP na porta 5052
- **Serialização:** JSON com Gson + TypeAdapter personalizado para polimorfismo
- **Pattern:** Request/Reply com IDs de requisição únicos
- **Transparência:** ClientProxy encapsula toda a complexidade de comunicação

O sistema simula funcionalidades reais dos Correios: envio, consulta de preços, listagem e entrega de volumes (cartas, encomendas e telegramas).

---

## 👥 Equipe

* **Luis Fernando Batista Lima** - 538134
* **Pedro Henrique Santos Moreira** - 536925
* **Curso:** Engenharia de Software

---

## 🏗️ Arquitetura Middleware UDP

- **Comunicação:** UDP na porta 5052
- **Serialização:** JSON com Gson + TypeAdapter personalizado
- **Pattern:** Request/Reply com IDs de requisição
- **Polimorfismo:** Suporte completo para hierarquia `Correspondencia`
- **Transparência:** ClientProxy oferece interface transparente para o cliente
- **Robustez:** Gestão automática de endereços e portas UDP

---

## 🧱 Estrutura do Projeto

### 📦 Organização dos Pacotes

- **modelo**: Classes do domínio (`Correspondencia`, `Carta`, `Encomenda`, `Telegrama`)
- **servico**: Serviço de entregas (`EntregasService`, `LojaCorreios`)
- **middleware.core**: Serialização JSON e servidor UDP (`Marshaller`, `CorrespondenciaTypeAdapter`, `ServidorMiddleware`)
- **middleware.proxy**: Proxy e cliente de teste UDP (`ClientProxy`, `TesteClientProxy`)
- **middleware.transport**: Estruturas de requisição/resposta e transporte UDP (`Request`, `Reply`, `RemoteObjectRef`, `RequestHandler`)

### 🔗 Relações Implementadas
- **2 Agregações:** LojaCorreios contém List&lt;Correspondencia&gt;
- **3 Heranças:** Carta, Encomenda, Telegrama estendem Correspondencia
- **4 Entidades POJO:** Todas as classes do modelo são serializáveis

---

## ✅ Funcionalidades

### 4 Métodos Remotos Implementados
1. **Registrar Correspondência** (ID: 0) - Adiciona nova correspondência ao sistema
2. **Listar Correspondências** (ID: 1) - Retorna todas as correspondências cadastradas
3. **Consultar Preço** (ID: 2) - Calcula preço baseado no tipo e características
4. **Entregar Correspondência** (ID: 3) - Marca correspondência como entregue

### 💰 Cálculo de Preços
- **Carta:** R$ 1,50 (normal) / R$ 2,00 (selada)
- **Encomenda:** R$ 5,00 por kg
- **Telegrama:** R$ 0,50 por palavra

---

## 🚀 Como Executar

### Pré-requisitos
```bash
cd d:/UFC/SDs/sistemas-distribuidos-2025.1/trabalho02
mvn compile
```

### 🔶 Middleware UDP

1. **Servidor Middleware:**
```bash
mvn exec:java -Dexec.mainClass="com.example.middleware.core.ServidorMiddleware"
```
> Servidor UDP aguardando na porta 5052

2. **Cliente de Teste:**
```bash
mvn exec:java -Dexec.mainClass="com.example.middleware.proxy.TesteClientProxy"
```
> Executa demonstração: registra carta e lista correspondências

---

## 🔗 Comunicação Remota

### Middleware UDP
- **Serialização:** JSON com suporte a polimorfismo via TypeAdapter personalizado
- **Request/Reply:** Pattern assíncrono com IDs únicos para correlação
- **Transparência:** ClientProxy encapsula todos os detalhes de comunicação UDP
- **Polimorfismo Distribuído:** Mantém hierarquia de classes através da rede
- **Gestão Automática:** Captura e resposta automática de endereços/portas UDP