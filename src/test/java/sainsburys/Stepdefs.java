package sainsburys;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.JsonObject;
import java.util.ArrayList;
import org.json.JSONObject;
import sainsburys.json.JsonBuilder;
import sainsburys.utils.Constants;
import sainsburys.utils.WebPageReader;
import cucumber.api.java.en.Then;
import static org.junit.Assert.*;

public class Stepdefs {

private Scenario scenario;
private String rawData;
private JSONObject jsonResult;
private JsonVerifier jsonVerifier;

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
        scenario.write("Loading sainsburys webpage");
        rawData = reader.getHtmlFromUrl(Constants.BERRIES_URL);
        assertTrue(rawData != null);
    }

    @When("^scrape the data$")
    public void scrape_the_data() {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonResult = jsonBuilder.getFullJsonData(Constants.BERRIES_URL);

        assertTrue(jsonResult != null);

        jsonVerifier = new JsonVerifier(jsonResult);
    }

    @When("^the correct total is displayed$")
    public void the_correct_total_is_displayed() {
        assertTrue(jsonVerifier.verifyTotal(39.5, 7.9));
    }

    @When("^the products all have a title$")
    public void the_products_all_have_a_title() {
        assertTrue(jsonVerifier.verifyTitleFields());
    }

    @When("^the products all have a single line description$")
    public void the_products_all_have_a_single_line_description() {
        assertTrue(jsonVerifier.verifyDescriptionFields());
    }

    @When("^the products all have a unit price field$")
    public void the_products_all_have_a_unit_price_field() {
        assertTrue(jsonVerifier.verifyPriceFields());
    }

    @When("^the products that should have calorie information, do$")
    public void the_products_that_should_have_calorie_information_do() {
        assertTrue(jsonVerifier.verifyCalories(getProductsWithoutCalories()));
    }

    private ArrayList<String> getProductsWithoutCalories(){
        ArrayList<String> namesOfProductsWithoutCalories = new ArrayList<String>();

        namesOfProductsWithoutCalories.add("Sainsbury's Mixed Berries 300g");
        namesOfProductsWithoutCalories.add("Sainsbury's Mixed Berry Twin Pack 200g");
        namesOfProductsWithoutCalories.add("Sainsbury's Blackcurrants 150g");
        namesOfProductsWithoutCalories.add("Sainsbury's British Cherry & Strawberry Pack 600g");

        return namesOfProductsWithoutCalories;

    }

    @Then("^the website has been scraped correctly$")
    public void the_website_has_been_scraped_correctly(){

    }
}