Feature: Scrape the Sainsbury's website
  Check the results from scraping the Sainsbury's website for Berries, Cherries, Currants

  Scenario: Scrape the website
    Given I can connect to the internet
    When I load the page
    And scrape the data
    And the correct total is displayed
    And the products all have a title
    And the products all have a single line description
    And the products all have a unit price field
    And the products that should have calorie information, do
    Then the website has been scraped correctly