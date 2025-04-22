FROM tomcat:8.5-jdk11-openjdk-slim
WORKDIR /app
COPY . .
RUN gradle clean bootWar

FROM tomcat:9-jre17
COPY --from=builder /build/libs/*.war /user

ENTRYPOINT ["java", "-war", "app.war"]