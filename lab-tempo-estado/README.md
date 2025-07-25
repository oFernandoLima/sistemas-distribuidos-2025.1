# Laboratório Tempo e Estado Global

Este laboratório implementa e demonstra os conceitos de tempo lógico em sistemas distribuídos através de três partes práticas.

## Estrutura do Projeto

```
src/
├── LaboratorioTempoEstado.java     # Arquivo principal
├── parte1/
│   ├── Processo.java               # Processo com relógio físico
│   └── SimuladorEventosDistribuidos.java
├── parte2/
│   ├── ProcessoLamport.java        # Processo com relógio de Lamport
│   └── SimuladorLamport.java
└── parte3/
    ├── ProcessoVetorial.java       # Processo com relógio vetorial
    └── SimuladorVetorial.java
```

## Como Executar

### 1. Compilação

**Windows:**
```bash
compilar.bat
```

**Linux/Mac:**
```bash
chmod +x compilar.sh
./compilar.sh
```

### 2. Execução

```bash
cd bin
java LaboratorioTempoEstado
```

## Partes do Laboratório

### Parte 1: Simulando Eventos Distribuídos
- **Objetivo:** Mostrar que relógios físicos podem não refletir a ordem causal dos eventos
- **Implementação:** 3 processos trocando mensagens com timestamps físicos
- **Observação:** A ordem dos timestamps não necessariamente reflete a ordem causal

### Parte 2: Relógio Lógico de Lamport
- **Objetivo:** Observar a consistência causal usando relógios lógicos
- **Regras:**
  - Evento interno: `L(p)++`
  - Envio: envia `L(p)` atual
  - Recepção: `L(p) = max(L(recebido), L(p)) + 1`
- **Propriedade:** Se evento A acontece antes de B, então `L(A) < L(B)`

### Parte 3: Relógios Vetoriais
- **Objetivo:** Detectar concorrência entre eventos
- **Regras:**
  - Evento local: `V[i][i]++`
  - Envio: envia cópia de `V[i]`
  - Recepção: `V[i][j] = max(V[i][j], Vmsg[j])` para todo j, depois `V[i][i]++`
- **Capacidades:**
  - Detecta relação happened-before
  - Identifica eventos concorrentes
  - Mantém ordem causal precisa

## Conceitos Demonstrados

1. **Relógio Físico vs Lógico:** Diferença entre tempo real e tempo lógico
2. **Ordenação Causal:** Como eventos distribuídos se relacionam causalmente
3. **Concorrência:** Identificação de eventos que não têm relação causal
4. **Algoritmo de Lamport:** Implementação prática do relógio lógico
5. **Relógios Vetoriais:** Detecção precisa de concorrência e causalidade

## Observações Importantes

- Cada parte usa portas diferentes para evitar conflitos
- Os processos usam sleeps aleatórios para simular timing real
- As mensagens incluem os valores dos relógios para análise
- A saída mostra claramente as relações causais e concorrentes

## Resultados Esperados

- **Parte 1:** Timestamps físicos podem estar "fora de ordem" causal
- **Parte 2:** Relógios lógicos respeitam ordem causal, mas não detectam concorrência
- **Parte 3:** Relógios vetoriais detectam tanto ordem causal quanto concorrência
