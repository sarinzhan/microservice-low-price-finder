FROM eclipse-temurin:23-jdk
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","kg.kazbekov.chatbot.ChatBotApplication"]


# ./gradlew build && mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*-SNAPSHOT.jar) &&  docker buildx build --platform linux/amd64 --build-arg DEPENDENCY=build/dependency -t jlet4uk/low-price-finder-chat-bot:1 . --push
