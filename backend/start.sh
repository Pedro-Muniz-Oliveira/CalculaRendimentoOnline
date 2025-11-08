#!/usr/bin/env bash
set -e

echo "🚀 Iniciando servidor Java..."

mkdir -p backend/build_classes

# Compila se ainda não existir
if [ ! -f backend/build_classes/CalculadoraServer.class ]; then
  echo "🔧 Compilando o código..."
  javac -cp backend/lib/json.jar -d backend/build_classes backend/src/CalculadoraServer.java
fi

# Executa
java -cp backend/build_classes:backend/lib/json.jar backend.CalculadoraServer
