#Sainsbruy's server side test

##List of technologies/libraries used
 - Cucumber and Gherkin to write tests
      - I chose this because it allows me to write a generic set of test steps before we know what the implementation will look like
      - I could then go along and fill in the step definitions as I completed different parts of the application
 - Maven to build the application
 - Jsoup to easily extract data from html
 - Json to easily create and verify json objects

##How to run the application
###Application is built using Maven
 - In a terminal navigate to the root directory for the project (continaing the pom.xml) and invoke
      - mvn package
      - this will create a jar file in the target folder called: sainsburys-1.0.0-SNAPSHOT.jar
 - to execute the program invoke (from the same folder):
      - java -jar target/sainsburys-1.0.0-SNAPSHOT.jar
      
##How to run the tests
The tests shoudld run as part of the maven package step but to run the tests independently 
 - in a terminal navigate to the root directory for the project and invoke
 - mvn test