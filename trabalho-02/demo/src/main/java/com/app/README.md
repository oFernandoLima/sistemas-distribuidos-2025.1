# 📦 Sistema de Controle de Volumes dos Correios (RMI)

Projeto desenvolvido para a disciplina **Sistemas Distribuídos** — UFC Campus Quixadá.

## 🧾 Descrição

O sistema simula um serviço remoto dos Correios para controle de correspondências, com funcionalidades de envio, consulta e entrega de volumes (cartas, encomendas e telegramas), utilizando Java RMI para comunicação entre cliente e servidor.

---

## 👥 Equipe

* Luis Fernando Batista Lima - 538134
* Pedro Henrique Santos Moreira - 536925
* Curso: Engenharia de Software

---

## 🧱 Estrutura do Projeto

- **Superclasse**
  - `Correspondencia`: classe abstrata base.
- **Subclasses**
  - `Carta`, `Encomenda`, `Telegrama`: especializações da correspondência.
- **Agregação**
  - `LojaCorreios`: armazena uma lista de correspondências.
- **Interface Remota**
  - `Entregas`: define os métodos que o cliente pode invocar remotamente.
- **Implementação**
  - `EntregasImpl`: implementação dos métodos remotos.
- **Cliente**
  - `ClienteCorreios`: interface de texto para interação do usuário.
- **Servidor**
  - `ServidorCorreios`: responsável por registrar o serviço remoto no RMI Registry.

---

## ✅ Funcionalidades

- Registrar diferentes tipos de correspondência.
- Consultar o preço de envio.
- Listar todas as correspondências registradas.
- Marcar uma correspondência como entregue.

---

## 🚀 Como Executar

### 1. Compilar todos os arquivos
```bash
javac *.java
````

### 2. Iniciar o servidor

```bash
java ServidorCorreios
```

> Isso iniciará o RMI Registry e registrará o serviço remoto como `EntregasService`.

### 3. Em outro terminal, iniciar o cliente

```bash
java ClienteCorreios
```

> A interface de texto será exibida com opções para registrar e manipular correspondências.

---

## 🔗 Comunicação Remota

* A comunicação entre cliente e servidor é feita via **Java RMI**.
* Passagem de parâmetros é feita:

  * Por valor: para as entidades POJO (`Carta`, `Encomenda`, etc.)
  * Por referência: para o objeto remoto `Entregas`.

---

## 📌 Requisitos Atendidos

* ✅ 4 entidades POJO (`Correspondencia`, `Carta`, `Encomenda`, `Telegrama`)
* ✅ 2 relações de agregação (Loja contém correspondências)
* ✅ 2 heranças ("é-um") entre subclasses e a superclasse
* ✅ 4 métodos remotos
* ✅ Passagem por valor e referência
* ✅ Comunicação sem sockets diretos (apenas RMI)