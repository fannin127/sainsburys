<h1>Sainsbruy's server side test</h1>

<h2>List of technologies/libraries used</h2>
<ul>
 <li>Cucumber and Gherkin to write tests</li>
 <ul>
 <li>I chose this because it allows me to write a generic set of test steps before we know what the implementation will look like</li>
   <li>I could then go along and fill in the step definitions as I completed different parts of the application</li>
 </ul>
 <li>Maven to build the application </li>
 <li>Jsoup to easily extract data from html </li>
 <li>Json to easily create and verify json objects </li>
</ul>
<h2>How to run the application</h2>
<h3>Application is built using Maven </h3>
<ul>
<li>In a terminal navigate to the root directory for the project (continaing the pom.xml) and invoke</li>
 <ul>
  <li>mvn package</li>
  <li>this will create a jar file in the target folder called: sainsburys-1.0.0-SNAPSHOT.jar</li>
 </ul>
 <li>to execute the program invoke (from the same folder):</li>
 <ul>
  <li>java -jar target/sainsburys-1.0.0-SNAPSHOT.jar</li>
 </ul>
      
<h2>How to run the tests</h2>
The tests shoudld run as part of the maven package step but to run the tests independently: 
<ul>
 <li>in a terminal navigate to the root directory for the project and invoke</li>
 <li>mvn test</li>
 </ul>
