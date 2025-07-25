@echo off

echo Compilando o laboratorio...

REM Cria diretorio bin se nao existir
if not exist bin mkdir bin

REM Compila todos os arquivos Java
javac -d bin src/*.java src/parte1/*.java src/parte2/*.java src/parte3/*.java

if %errorlevel% equ 0 (
    echo Compilacao concluida com sucesso!
    echo.
    echo Para executar:
    echo cd bin ^&^& java LaboratorioTempoEstado
) else (
    echo Erro na compilacao!
)
