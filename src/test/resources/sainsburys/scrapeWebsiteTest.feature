Feature: Scrape the sainsburys website
  Check the results from scraping the sainsburys website for Berries, Cherries, Currants

  Scenario: Scrape the website
    Given I can connect to the internet
    When I load the page
    And scrape the data
    And the data is validated
    Then the website has been scraped correctly