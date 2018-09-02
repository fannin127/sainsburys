package sainsburys.json;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sainsburys.utils.Constants;
import sainsburys.utils.WebPageReader;

/**
 * Used to construct the product data in JSON format from the Sainsbury's website
 */
public class JsonBuilder {

  private double priceTotal;;

  /**
   * Simple constructor to initialise variables
   */
  public JsonBuilder() {
    priceTotal = 0;
  }


  /**
   * The public facing method of the class gets the full json data scraped from the given URL
   *
   * @param url url to scrape
   * @return returns the full json object of the data on the page
   */
  public JSONObject getFullJsonData(String url) {
    priceTotal = 0;

    //Open the given webpage and get a Jsoup document
    WebPageReader webPageReader = new WebPageReader();
    Document document = webPageReader.getDocument(url);
    //A series of divs with the procuctInfor class
    Elements productDivElements = document.getElementsByClass("productInfo");

    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();

    //iterate through all the products and get the individual product data using the hyperlink
    for (Element element : productDivElements) {
      Element relHref = element.select("a").first();
      jsonArray.put(getProductJsonData(relHref.attr("href")));
    }

    //create the total (gross and vat) object
    JSONObject totalObject = new JSONObject();
    totalObject.put("gross", unitPriceToTwoDecimalPlaces(Double.toString(priceTotal)));
    totalObject.put("vat", unitPriceToTwoDecimalPlaces(Double.toString(priceTotal * 0.2)));

    //package the json object together
    jsonObject.put("results", jsonArray);
    jsonObject.put("total", totalObject);

    return jsonObject;
  }

  /**
   * Given a string price, a zero will be added to the end if there are not two decimal places after
   * the .
   *
   * @param unitPrice string representation of the price
   * @return a new unit price string with two decimal places
   */
  private String unitPriceToTwoDecimalPlaces(String unitPrice) {
    if (!(unitPrice.split("\\.")[1].length() == 2)) {
      unitPrice += 0;
    }
    return unitPrice;
  }

  /**
   * private method to get json data for a single product
   *
   * @param url url of product page to scrape
   * @return returns json object with product data
   */
  private JSONObject getProductJsonData(String url) {
    //open the specific product page given from the URL
    WebPageReader webPageReader = new WebPageReader();
    Document document = webPageReader.getDocument(Constants.BASE_URL + url);

    //create a json object and create the fields required to fill it
    JSONObject jso = new JSONObject();
    String title = document.select("h1").first().text();
    String price = unitPriceToTwoDecimalPlaces(document.getElementsByClass(
        "pricePerUnit").first().text().split("/")[0].substring(2));
    priceTotal += Double.parseDouble(price);
    String description = getFirstNonNullTextFromElements(
        document.getElementsByClass("productText").first().select("p"));
    String calories;

    //try catch used in case a given product does not have calorie information
    try {
      calories = document.select("tr:contains(kcal)").get(0).select("td").get(0).text();
    } catch (Exception e) {
      //exception thrown due to product not having calorie information
      calories = null;
    }

    //put all the fields into the json object
    jso.put("title", title);
    if (calories != null) {
      jso.put("kcal_per_100g", calories);
    }
    jso.put("unit_price", price);
    jso.put("description", description);

    return jso;
  }

  /**
   * finds the first non-empty text value from within a set of elements
   *
   * @param elements elements to search
   * @return a string with the first non-empty text part of an element will return null if no
   * elements have text
   */
  private String getFirstNonNullTextFromElements(Elements elements) {
    for (Element e : elements) {
      if (!e.text().equals("")) {
        return e.text();
      }
    }
    return null;
  }
}