#Usse a base image with Java installed
FROM openjdk:22
# Set the working directoy in the containerr
WORKDIR /app
# Copy the  JAR file into the container at /app
COPY target/*.jar app.jar
#S pecify the command to run your application
CMD ["java", "-jar", "app.jar"]
#ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]