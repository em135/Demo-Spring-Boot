FROM openjdk:8-jdk-alpine
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

ENV SERVERDIRNAME reviews

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
#
ARG service_version
ARG enable_ratings
ARG star_color

ENV SERVICE_VERSION ${service_version:-v1}
ENV ENABLE_RATINGS ${enable_ratings:-false}
ENV STAR_COLOR ${star_color:-black}

EXPOSE 9080

RUN wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent-all.jar

CMD ["java", "-javaagent:opentelemetry-javaagent-all.jar", \
"-Dotel.resource.attributes=service.name=demo", \
"-Dotel.traces.exporter=none", \
"-Dotel.metrics.exporter=none", \
"-Dotel.propagators=b3multi", \
"-Dotel.javaagent.debug=true", \
"-jar","/app.jar"]
#ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["java","-jar","/app.jar"]
