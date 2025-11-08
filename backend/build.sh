#!/usr/bin/env bash
set -e

echo "⚙️ Instalando JDK..."
apt-get update -y
apt-get install -y openjdk-17-jdk

echo "✅ JDK instalado."
