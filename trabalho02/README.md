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

2. **Cliente Interativo (Recomendado):**
```bash
mvn exec:java -Dexec.mainClass="com.example.correios.app.ClienteInterativo"
```
> Interface interativa completa pelo terminal com menu de opções

3. **Demonstração Automatizada:**
```bash
mvn exec:java -Dexec.mainClass="com.example.correios.app.DemoCliente"
```
> Executa demonstração completa: registra múltiplas correspondências, lista, consulta preços e entrega

4. **Cliente de Teste Simples:**
```bash
mvn exec:java -Dexec.mainClass="com.example.middleware.proxy.TesteClientProxy"
```
> Teste básico: registra uma carta e lista correspondências

---

## 🖥️ Cliente Interativo

O **ClienteInterativo** oferece uma interface completa pelo terminal para interagir com o sistema de correios:

### 📋 Funcionalidades do Menu
- **1️⃣ Registrar Correspondência:** Cadastro interativo de cartas, encomendas e telegramas
- **2️⃣ Listar Correspondências:** Visualização em tabela formatada com preços
- **3️⃣ Consultar Preço:** Busca por código específico com regras de preço
- **4️⃣ Entregar Correspondência:** Marca como entregue e remove do sistema
- **0️⃣ Sair:** Encerra o cliente

### 🎯 Exemplo de Uso
```bash
# 1. Inicie o servidor middleware
mvn exec:java -Dexec.mainClass="com.example.middleware.core.ServidorMiddleware"

# 2. Em outro terminal, execute o cliente interativo
mvn exec:java -Dexec.mainClass="com.example.correios.app.ClienteInterativo"

# 3. Siga o menu interativo para usar todas as funcionalidades
```

### 📊 Recursos do Cliente
- **Interface Amigável:** Emojis e formatação visual clara
- **Validação de Entrada:** Tratamento de erros e entradas inválidas
- **Tabelas Formatadas:** Listagem organizada com alinhamento automático
- **Cálculo Automático:** Exibição de preços individuais e totais
- **Truncamento Inteligente:** Textos longos são cortados adequadamente

### 💡 Exemplo de Interação
```
📋 MENU PRINCIPAL:
1️⃣  Registrar Correspondência
2️⃣  Listar Correspondências
3️⃣  Consultar Preço
4️⃣  Entregar Correspondência
0️⃣  Sair
➡️  Escolha uma opção: 1

📝 REGISTRAR NOVA CORRESPONDÊNCIA
Tipos disponíveis:
1 - Carta
2 - Encomenda
3 - Telegrama
➡️  Tipo: 1
📮 Código: CARTA001
📫 Destinatário: João Silva
🏠 Endereço: Rua das Flores, 123
📧 É selada? (s/n): s

✅ Correspondência registrada com sucesso!
📄 Detalhes:
  • Código: CARTA001
  • Tipo: Carta
  • Destinatário: João Silva
  • Endereço: Rua das Flores, 123
  • Preço: R$ 2,00
```