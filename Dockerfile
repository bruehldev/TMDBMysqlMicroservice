FROM openjdk:8
ADD target/TMDBMysqlMicroservice.jar TMDBMysqlMicroservice.jar
EXPOSE 8762
ENTRYPOINT ["java", "-jar", "TMDBMysqlMicroservice.jar"]