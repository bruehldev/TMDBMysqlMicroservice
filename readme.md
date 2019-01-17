TMDB Microservice running as springboot application

# Preparations
Insert tmdb api key in TMDB\src\main\java\com\AWS\TMDB\TmdbController.java
Change directory to TMDB.

# Create JAR
mvn clean package

# Run JAR
target/java -jar TMDBMysqlMicroservice.jar

# Use microservice
In order to use this service type [IP]:8762/[Series ID] in browser to search.

# Create Docker image
docker build .