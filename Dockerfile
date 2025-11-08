# Usa imagem base com Java 17 já instalado
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia tudo para dentro do container
COPY . .

# Compila o backend Java
RUN javac -cp .:backend/json.jar backend/CalculadoraServer.java

# Expõe a porta 8080
EXPOSE 8080

# Roda o servidor Java
CMD ["java", "-cp", ".:backend/json.jar", "backend.CalculadoraServer"]
