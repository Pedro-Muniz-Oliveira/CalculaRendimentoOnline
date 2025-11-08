# Usa imagem oficial com Java 17 + Node.js
FROM openjdk:17-jdk-slim

# Instala Node.js e Yarn (para o frontend)
RUN apt-get update && apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_22.x | bash - && \
    apt-get install -y nodejs yarn && \
    apt-get clean

# Define diretório de trabalho
WORKDIR /app

# Copia tudo para dentro do container
COPY . .

# Compila o backend
RUN javac -cp .:backend/json.jar backend/CalculadoraServer.java

# Expõe a porta que o servidor Java usa
EXPOSE 8080

# Comando para iniciar o servidor
CMD ["java", "-cp", ".:backend/json.jar", "backend.CalculadoraServer"]
