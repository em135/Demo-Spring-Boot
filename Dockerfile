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

RUN wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.0.1/opentelemetry-javaagent-all.jar
#"-Dotel.traces.exporter=none", \
CMD ["java", "-javaagent:opentelemetry-javaagent-all.jar", \
"-Dotel.resource.attributes=service.name=demo", \
"-Dotel.traces.exporter=zipkin", \
"-Dotel.exporter.zipkin.endpoint=http://otel-collector.istio-system:9411/api/v1/spans", \
"-Dotel.metrics.exporter=none", \
"-Dotel.propagators=b3multi", \
"-Dotel.javaagent.debug=true", \
"-jar","/app.jar"]
#ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["java","-jar","/app.jar"]
