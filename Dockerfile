# ===== BUILD STAGE =====
FROM amazoncorretto:17-alpine AS build

WORKDIR /build

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

COPY src/ src/
RUN ./mvnw clean package -DskipTests


# ===== RUNTIME STAGE =====
FROM amazoncorretto:17-alpine

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "app.jar"]
