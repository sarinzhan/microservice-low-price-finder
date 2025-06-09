FROM mcr.microsoft.com/playwright/java:v1.49.0-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# make it layered

# ./gradlew build && docker buildx build --platform linux/amd64 --build-arg JAR_FILE=build/libs/\*.jar -t jlet4uk/low-price-finder-parser:3 . --push
# run following command to create image and push it

#docker buildx create --use --driver docker