package sainsburys;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import sainsburys.utils.Constants;
import sainsburys.utils.WebPageReader;
import cucumber.api.java.en.Then;
import static org.junit.Assert.*;

public class Stepdefs {

private Scenario scenario;
private String rawData;

@Before
public void beforeTest(Scenario scenario){
    this.scenario = scenario;
}
    @Given("^I can connect to the internet$")
    public void i_can_connect_to_the_internet()  {
        WebPageReader reader = new WebPageReader();
        scenario.write("Making a connection to the internet");
        assertTrue(reader.getHtmlFromUrl("https://google.com") != null);
    }  

    @When("^I load the page$")
    public void i_load_the_page()  {
        WebPageReader reader = new WebPageReader();
        rawData = reader.getHtmlFromUrl(Constants.CONNECTION_URL);
        assertTrue(rawData != null);
    }

    @When("^scrape the data$")
    public void scrape_the_data() {
        
    }

    @When("^the data is validated$")
    public void the_data_is_validated(){

    }   

    @Then("^the website has been scraped correctly$")
    public void the_website_has_been_scraped_correctly(){

    }
}