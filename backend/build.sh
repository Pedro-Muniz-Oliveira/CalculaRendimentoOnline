#!/usr/bin/env bash
echo "⚙️ Instalando o JDK 17..."
apt-get update -y
apt-get install -y openjdk-17-jdk

echo "✅ JDK instalado com sucesso!"
java -version
