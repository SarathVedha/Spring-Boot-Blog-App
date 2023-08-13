FROM eclipse-temurin:17
LABEL authors="Vedha"

WORKDIR /app

COPY target/SpringBootBlogApp-0.0.1-SNAPSHOT.jar /app/SpringBootBlogApp.jar

ENTRYPOINT ["java", "-jar", "SpringBootBlogApp.jar"]