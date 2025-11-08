#!/usr/bin/env bash
set -e  # interrompe se ocorrer erro

echo "==> Compilando o servidor Java..."

# Cria pasta para classes compiladas
mkdir -p backend/build_classes

# Compila o servidor
javac -cp backend/lib/json.jar -d backend/build_classes backend/src/CalculadoraServer.java

echo "==> Iniciando servidor..."
java -cp backend/build_classes:backend/lib/json.jar CalculadoraServer
