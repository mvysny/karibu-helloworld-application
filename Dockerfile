# Allows you to run this app easily as a docker container.
# See README.md for more details.
#
# 1. Build the image with: docker build -t test/karibu-helloworld-application:latest .
# 2. Run the image with: docker run --rm -ti -p8080:8080 test/karibu-helloworld-application
#
# Uses Docker Multi-stage builds: https://docs.docker.com/build/building/multi-stage/

# The "Build" stage. Copies the entire project into the container, into the /app/ folder, and builds it.
FROM eclipse-temurin:21 AS builder
COPY . /app/
WORKDIR /app/
RUN --mount=type=cache,target=/root/.gradle,sharing=locked --mount=type=cache,target=/root/.vaadin,sharing=locked ./gradlew clean build -Pvaadin.productionMode --no-daemon --no-watch-fs
WORKDIR /app/build/distributions/
# The archive, the folder it unpacks to and the start script inside it are all named after
# rootProject.name, pinned in settings.gradle.kts.
RUN tar xvf karibu-helloworld-application.tar
# At this point, we have the app (executable bash scrip plus a bunch of jars) in the
# /app/build/distributions/karibu-helloworld-application/ folder.

# The "Run" stage. Start with a clean image, and copy over just the app itself, omitting gradle, npm and any intermediate build files.
FROM eclipse-temurin:21
COPY --from=builder /app/build/distributions/karibu-helloworld-application /app/
WORKDIR /app/bin
EXPOSE 8080
ENTRYPOINT ["./karibu-helloworld-application"]

