#!/usr/bin/env bash
set -e

echo "🔧 Compilando servidor Java..."
mkdir -p out
javac -d out -cp .:backend/json.jar backend/CalculadoraServer.java
echo "✅ Compilado com sucesso!"
