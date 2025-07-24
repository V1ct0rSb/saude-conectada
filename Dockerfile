# Estágio 1: Compilação (Build)
# Usamos uma imagem que já tem o Maven e o Java 17 (necessário para o Spring Boot 3)
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo de configuração do Maven para baixar as dependências primeiro
COPY pom.xml .

# Baixa todas as dependências (isso acelera builds futuros)
RUN mvn dependency:go-offline

# Copia o resto do código-fonte do seu projeto
COPY src ./src

# Roda o comando para "empacotar" sua aplicação em um arquivo .jar
RUN mvn package -DskipTests


# Estágio 2: Execução (Runtime)
# Usamos uma imagem muito menor, que só tem o Java para rodar a aplicação
FROM eclipse-temurin:17-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas o arquivo .jar que foi gerado no estágio de compilação
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080 para que o Render possa se comunicar com sua API
EXPOSE 8080

# Comando final para iniciar sua aplicação Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]