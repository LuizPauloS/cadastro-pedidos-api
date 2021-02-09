FROM adoptopenjdk/openjdk11-openj9:alpine-slim

ENV JAVA_OPTS=" -server -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.country=BR -Dfile.encoding=UTF-8"
WORKDIR /api

COPY ./target/*.jar /api/cadastro-pedidos-api.jar

EXPOSE 9000

CMD java $JAVA_OPTS -jar cadastro-pedidos-api.jar