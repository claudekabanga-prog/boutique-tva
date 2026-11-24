# Image de la boutique TVA : Java 21 (runtime seulement)
FROM eclipse-temurin:21-jre

# La version est donnee par Jenkins : --build-arg APP_VERSION=1.0.12
ARG APP_VERSION=dev
ENV APP_VERSION=${APP_VERSION}

WORKDIR /app
# Jar construit par le pipeline (etape Package)
COPY build/boutique-tva.jar app.jar

# L'application ecoute sur le port 8080 dans le conteneur
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]