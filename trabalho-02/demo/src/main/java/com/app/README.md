# Sistema de Correios - RMI Personalizado

Sistema distribuído para gerenciamento de correspondências (cartas, encomendas e telegramas) implementado com protocolo RMI personalizado conforme especificação do Trabalho 2 da disciplina de Sistemas Distribuídos.

## 📋 Pré-requisitos

- Java 11 ou superior
- Maven 3.6+
- Porta 9999 disponível

## 🏗️ Estrutura do Projeto

```
src/main/java/com/app/
├── client/
│   ├── ClientProxy.java          # Proxy do cliente (protocolo RMI)
│   └── ClienteCorreios.java      # Interface do cliente
├── domain/
│   ├── Correspondencia.java      # Classe abstrata base
│   ├── Carta.java               # Herda de Correspondencia
│   ├── Encomenda.java           # Herda de Correspondencia
│   ├── Telegrama.java           # Herda de Correspondencia
│   ├── LojaCorreios.java        # Agregação com Correspondencia
│   └── Funcionario.java         # Segunda agregação
├── protocol/
│   ├── RemoteObjectRef.java     # Referência de objeto remoto
│   ├── RequestMessage.java      # Mensagem de requisição
│   ├── ReplyMessage.java        # Mensagem de resposta
│   └── MessageSerializer.java   # Serialização JSON
└── server/
    ├── ServerDispatcher.java    # Despachador do servidor
    └── ServidorCorreios.java    # Servidor principal
```

## 🚀 Como Executar

### 1. Compilar o Projeto
```bash
mvn clean compile
```

### 2. Executar o Servidor
```bash
mvn exec:java -Dexec.mainClass="com.app.server.ServidorCorreios"
```

**Saída esperada:**
```
Iniciando servidor de correios na porta 9999...
Servidor pronto para receber requisições.
```

### 3. Executar o Cliente (em outro terminal)
```bash
mvn exec:java -Dexec.mainClass="com.app.client.ClienteCorreios"
```

## 📱 Como Usar o Sistema

### Menu Principal
```
--- MENU ---
1. Registrar Carta
2. Registrar Encomenda
3. Registrar Telegrama
4. Listar Correspondências
5. Consultar Preço
6. Entregar Correspondência
0. Sair
```

### Exemplos de Uso

#### 1. Registrar uma Carta
```
Opção: 1
Código: CARTA001
Destinatário: João Silva
Endereço: Rua das Flores, 123
Está selada (S/N)? S
Carta registrada.
```

#### 2. Registrar uma Encomenda
```
Opção: 2
Código: ENC001
Destinatário: Maria Santos
Endereço: Av. Central, 456
Peso (kg): 2.5
Encomenda registrada.
```

#### 3. Registrar um Telegrama
```
Opção: 3
Código: TEL001
Destinatário: Pedro Oliveira
Endereço: Rua do Comércio, 789
Número de palavras: 25
Telegrama registrado.
```

#### 4. Consultar Preço
```
Opção: 5
Informe o código: CARTA001
Preço: R$2.0
```

## 💰 Cálculo de Preços

- **Carta**: R$ 2,00 (selada) ou R$ 1,50 (não selada)
- **Encomenda**: R$ 5,00 por kg
- **Telegrama**: R$ 0,50 por palavra

## 🔧 Arquitetura Técnica

### Protocolo RMI Personalizado

O sistema implementa os métodos específicos da especificação:

- **`doOperation()`**: Envia requisição e retorna resposta
- **`getRequest()`**: Obtém requisição do cliente
- **`sendReply()`**: Envia resposta para o cliente

### Formato de Mensagens

**Requisição:**
```json
{
  "requestId": 1,
  "objectReference": "EntregasService",
  "methodId": "registrarCorrespondencia",
  "arguments": "..."
}
```

**Resposta:**
```json
{
  "requestId": 1,
  "result": "..."
}
```

### Comunicação

- **Protocolo**: UDP (DatagramSocket)
- **Porta**: 9999
- **Serialização**: JSON (Gson)
- **Representação Externa**: JSON para passagem por valor

## 📦 Dependências (pom.xml)

```xml
<dependencies>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
</dependencies>
```

## ⚙️ Configuração Alternativa do Maven

Para facilitar a execução, adicione ao `pom.xml`:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>3.1.0</version>
            <executions>
                <execution>
                    <id>servidor</id>
                    <configuration>
                        <mainClass>com.app.server.ServidorCorreios</mainClass>
                    </configuration>
                </execution>
                <execution>
                    <id>cliente</id>
                    <configuration>
                        <mainClass>com.app.client.ClienteCorreios</mainClass>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

**Execução com IDs:**
```bash
mvn exec:java@servidor    # Executar servidor
mvn exec:java@cliente     # Executar cliente
```


## 📝 Conformidade com a Especificação

✅ **Métodos RMI Personalizados Implementados**  
✅ **Formato de Mensagem Conforme Especificação**  
✅ **4+ Classes Entidade**  
✅ **2+ Composições "tem-um" (Agregação)**  
✅ **3+ Composições "é-um" (Herança)**  
✅ **4 Métodos de Invocação Remota**  
✅ **Passagem por Referência e Valor**  
✅ **Representação Externa de Dados (JSON)**  



