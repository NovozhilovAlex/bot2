FROM maven:3.6.3-jdk-11

# 1. Add pom.xml only here
ADD ./pom.xml ./pom.xml

# 2. Start downloading dependencies
RUN ["mvn", "verify", "clean", "--fail-never"]

# 3. Add all source code and start compiling
ADD ./src ./src

RUN ["mvn", "package"]

EXPOSE 8081

CMD ["java", "-jar", "./target/gits-file-service-0.0.1-SNAPSHOT.jar"]