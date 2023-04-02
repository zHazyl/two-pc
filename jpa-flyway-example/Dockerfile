#stage 1
# Start with a base image contain
FROM openjdk:17-slim as build

# Add Maintainer Info
LABEL maintainer="Tran Nguyen Hanh tnh210302@gmail.com"

# The application's jar file
ARG JAR_FILE

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

# Unpackage jar file
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

#stage2
#Same Java runtime
FROM openjdk:17-slim

# Add volume pointing to /tmp
VOLUME /tmp

# Copy unpackaged application to new container
ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Execute the application
ENTRYPOINT ["java","-cp","app:app/lib/*","com.tnh.jpaflywayexample.JpaFlywayExampleApplication"]