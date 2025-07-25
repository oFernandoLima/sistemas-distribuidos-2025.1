#!/bin/bash

echo "Compilando o laboratório..."

# Cria diretório bin se não existir
mkdir -p bin

# Compila todos os arquivos Java
javac -d bin src/*.java src/parte1/*.java src/parte2/*.java src/parte3/*.java

if [ $? -eq 0 ]; then
    echo "Compilação concluída com sucesso!"
    echo ""
    echo "Para executar:"
    echo "cd bin && java LaboratorioTempoEstado"
else
    echo "Erro na compilação!"
fi
