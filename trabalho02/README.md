# Sistema de Correios - Java RMI

Sistema de correios distribuído usando Java RMI (Remote Method Invocation).

---

## 👥 Equipe

* **Luis Fernando Batista Lima** - 538134
* **Pedro Henrique Santos Moreira** - 536925
* **Curso:** Engenharia de Software

---

## 📋 Funcionalidades

Operações disponíveis via RMI:

1. **Registrar Correspondência**: Adiciona carta, encomenda ou telegrama
2. **Listar Correspondências**: Exibe todas as correspondências cadastradas
3. **Consultar Preço**: Consulta preço por código
4. **Entregar Correspondência**: Remove correspondência do sistema
5. **Obter Nome da Loja**: Retorna nome da loja

## 📦 Tipos de Correspondência

### Carta
- Parâmetros: código, destinatário, endereço, selada (boolean)
- Preço: R$ 2,00 (selada) ou R$ 1,50 (não selada)

### Encomenda
- Parâmetros: código, destinatário, endereço, peso (kg)
- Preço: R$ 5,00 por kg

### Telegrama
- Parâmetros: código, destinatário, endereço, número de palavras
- Preço: R$ 0,50 por palavra

## 🔧 Tecnologias

- **Java RMI**: Comunicação distribuída
- **Maven**: Gerenciamento de dependências
- **Java 8+**: Linguagem de programação

## 📖 Conceitos RMI

1. **Interface Remota**: `EntregasServiceRemote` estende `Remote`
2. **Implementação Remota**: `EntregasService` estende `UnicastRemoteObject`
3. **Registro RMI**: Uso de `LocateRegistry` e `Naming`
4. **Serialização**: Objetos implementam `Serializable`
5. **Tratamento de Exceções**: `RemoteException` em métodos remotos

## 📁 Estrutura do Projeto

```
src/main/java/com/example/
├── correios/
│   ├── app/
│   │   ├── ServidorRMI.java      # Servidor RMI
│   │   ├── ClienteRMI.java       # Cliente interativo RMI
│   │   └── DemoClienteRMI.java   # Cliente de demonstração
│   ├── modelo/
│   │   ├── Correspondencia.java  # Classe abstrata base
│   │   ├── Carta.java           # Implementação de carta
│   │   ├── Encomenda.java       # Implementação de encomenda
│   │   └── Telegrama.java       # Implementação de telegrama
│   └── servico/
│       ├── EntregasServiceRemote.java  # Interface remota
│       ├── EntregasService.java        # Implementação do serviço
│       └── LojaCorreios.java          # Modelo da loja
```

## 🚀 Como Executar

### 1. Compilar
```bash
mvn compile
```

### 2. Iniciar Servidor RMI
```bash
mvn exec:java -Dexec.mainClass="com.example.correios.app.ServidorRMI"
```

### 3. Executar Cliente de Demonstração (Opcional)
```bash
mvn exec:java -Dexec.mainClass="com.example.correios.app.DemoClienteRMI"
```

### 4. Executar Cliente Interativo
```bash
mvn exec:java -Dexec.mainClass="com.example.correios.app.ClienteRMI"
```