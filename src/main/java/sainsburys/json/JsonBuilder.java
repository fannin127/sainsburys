package sainsburys.json;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sainsburys.utils.Constants;
import sainsburys.utils.WebPageReader;

public class JsonBuilder {
    private double priceTotal;

    /**
     * Simple constructor to initialise variables
     */
    public JsonBuilder(){
        priceTotal = 0;
    }


    /**
     * The public facing method of the class
     * gets the full json data scraped from the given URL
     * @param url url to scrape
     * @return returns the full json object of the data on the page
     */
    public JSONObject getFullJsonData(String url){
        priceTotal = 0;

        WebPageReader webPageReader = new WebPageReader();
        Document document = webPageReader.getDocument(url);
        Elements productDivElements = document.getElementsByClass("productInfo");

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Element element : productDivElements){
            Element relHref = element.select("a").first();
            jsonArray.put(getProductJsonData(relHref.attr("href")));
        }

        JSONObject totalObject = new JSONObject();
        totalObject.put("gross", priceTotal);
        totalObject.put("vat", priceTotal * 0.2);

        jsonObject.put("results", jsonArray);
        jsonObject.put("total", totalObject);

        return jsonObject;
    }

    /**
     * private method to get json data for a single product
     * @param url url of product page to scrape
     * @return returns json object with product data
     */
    private JSONObject getProductJsonData(String url){
        WebPageReader webPageReader = new WebPageReader();
        Document document = webPageReader.getDocument(Constants.BASE_URL + url);

        //using first might not be the best way to do this
        //if the website changes then it will return a different value
        //however if the website changes then the class names might change too

        JSONObject jso = new JSONObject();
        String title = document.select("h1").first().text();
        String price = document.getElementsByClass("pricePerUnit").first().text().split("/")[0].substring(2);
        priceTotal += Double.parseDouble(price);
        String description = getFirstNonNullTextFromElements(document.getElementsByClass("productText").first().select("p"));
        String calories;


        try {
          calories = document.select("tr:contains(kcal)").get(0).select("td").get(0).text();
        } catch (Exception e2){
          calories = null;
        }

        jso.put("title", title);
        if (calories != null){
            jso.put("kcal_per_100g", calories);
        }
        jso.put("unit_price", price);
        jso.put("description", description);


        return jso;
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