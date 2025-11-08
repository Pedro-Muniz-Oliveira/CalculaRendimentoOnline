#!/usr/bin/env bash
set -e

echo "==> Iniciando servidor Java no Render..."

# Caminho de compilação
mkdir -p backend/build_classes

# Compila se ainda não estiver compilado
if [ ! -f backend/build_classes/CalculadoraServer.class ]; then
  echo "==> Compilando CalculadoraServer..."
  javac -cp backend/lib/json.jar -d backend/build_classes backend/src/CalculadoraServer.java
fi

# Executa o servidor
java -cp backend/build_classes:backend/lib/json.jar backend.CalculadoraServer
