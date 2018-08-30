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

    private String convertToJsonLinePair(String column, String value, boolean isString){
        if (isString){
            return "\t\t\"" + column + "\": \"" + value + "\"";
        } else {
            return "\t\t\"" + column + "\": " + value;
        }

    }

    private String getFirstNonNullTextFromElements(Elements elements){
        for (Element e: elements) {
            if (!e.text().equals("")){
                return e.text();
            }
        }

        return null;
    }
}