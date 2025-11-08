# Usa imagem base com Java 17 já instalado
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia o conteúdo do projeto para o container
COPY . .

# Compila o backend (ajuste se o nome da classe principal for diferente)
RUN javac -cp .:backend/json.jar backend/CalculadoraServer.java

# Expõe a porta 8080
EXPOSE 8080

# Comando que roda o servidor
CMD ["java", "-cp", ".:backend/json.jar", "backend.CalculadoraServer"]

