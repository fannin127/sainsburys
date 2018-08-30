package sainsburys.json;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sainsburys.utils.Constants;
import sainsburys.utils.WebPageReader;

public class JsonBuilder {

    /**
     * The public facing method of the class
     * gets the full json data scraped from the given URL
     * @param url url to scrape
     * @return returns the full json object of the data on the page
     */
    public String getFullJsonData(String url){
        WebPageReader webPageReader = new WebPageReader();
        Document document = webPageReader.getDocument(url);
        Elements productDivElements = document.getElementsByClass("productInfo");

        String fullJson = "{ \n\t\"results\": [\n";
        for (Element element : productDivElements){
            Element relHref = element.select("a").first();
            fullJson += getProductJsonData(relHref.attr("href"));
        }

        fullJson += "\n],\n";
        return fullJson;
    }

    /**
     * private method to get json data for a single product
     * @param url url of product page to scrape
     * @return returns json object with product data
     */
    private String getProductJsonData(String url){
        WebPageReader webPageReader = new WebPageReader();
        Document document = webPageReader.getDocument(Constants.BASE_URL + url);

        //using first might not be the best way to do this
        //if the website changes then it will return a different value
        //however if the website changes then the class names might change too
        String title = document.select("h1").first().text();
        String price = document.getElementsByClass("pricePerUnit").first().text().split("/")[0].substring(2);
        String description = getFirstNonNullTextFromElements(document.getElementsByClass("productText").first().select("p"));
        String calories;

        try {
            calories = document.getElementsByClass("nutritionLevel1").first().text().split("kcal")[0];
        } catch (NullPointerException e){
            calories = null;
        }


        String jsonToReturn = "\t{ \n" + convertToJsonLinePair("title", title, true) + ", \n";
        if (calories != null){
            jsonToReturn += convertToJsonLinePair("kcal_per_100g", calories, false) + ", \n";
        }
        jsonToReturn += convertToJsonLinePair("unit_price", price, false) + ", \n";
        jsonToReturn += convertToJsonLinePair("description", description, true) + "\n";
        jsonToReturn += "\t} \n";

        return jsonToReturn;
    }

    /**
     * Private method, converts a title and it's value into a line for a json object
     * @param column title of the value in the object
     * @param value the value itself
     * @param isString whether or not the value is a string and needs quotes
     * @return returns a line for a json object
     */
    private String convertToJsonLinePair(String column, String value, boolean isString){
        if (isString){
            return "\t\t\"" + column + "\": \"" + value + "\"";
        } else {
            return "\t\t\"" + column + "\": " + value;
        }

    }

    /**
     * finds the first non-empty text value from within a set of elements
     * @param elements elements to search
     * @return a string with the first non-empty text part of an element
     *          will return null if no elements have text
     */
    private String getFirstNonNullTextFromElements(Elements elements){
        for (Element e: elements) {
            if (!e.text().equals("")){
                return e.text();
            }
        }
        return null;
    }
}