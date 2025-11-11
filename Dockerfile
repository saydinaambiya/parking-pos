FROM eclipse-temurin:17 AS build
WORKDIR /
COPY pom.xml .
COPY src src

COPY mvnw .
COPY .mvn .mvn

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/parking-pos-0.0.1.jar"]
EXPOSE 8080