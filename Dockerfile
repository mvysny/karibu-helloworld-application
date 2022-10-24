# Allows you to run this app easily in a docker image.
# See README.md for more details.
#
# 1. Build the image with: docker build --no-cache -t test/karibu10-helloworld-application:latest .
# 2. Run the image with: docker run --rm -ti -p8080:8080 test/karibu10-helloworld-application
#
# Uses Docker Multi-stage builds: https://docs.docker.com/build/building/multi-stage/

# The "Build" stage. Copies the entire project into the container, into the /karibu10-helloworld-application/ folder, and builds it.
FROM openjdk:11 AS BUILD
COPY . /app/
WORKDIR /app/
RUN ./gradlew clean test --no-daemon --info --stacktrace
RUN ./gradlew build -Pvaadin.productionMode --no-daemon --info --stacktrace
# At this point, we have the WAR app: /app/build/libs/app.war

# The "Run" stage. Start with a clean image, and copy over just the app itself, omitting gradle, npm and any intermediate build files.
FROM tomcat:9.0.68-jre17-temurin-jammy
COPY --from=BUILD /app/build/libs/app.war /usr/local/tomcat/webapps/ROOT.war

