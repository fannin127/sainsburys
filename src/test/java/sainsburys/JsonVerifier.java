package sainsburys;


import gherkin.deps.com.google.gson.JsonObject;
import java.util.ArrayList;
import org.json.JSONObject;

public class JsonVerifier {
  JSONObject jsonObject;

  public JsonVerifier(JSONObject jsonObject){
    this.jsonObject = jsonObject;
  }

  public boolean verifyTotal(double expectedGross, double expectedVat){
    JSONObject totalObject = jsonObject.getJSONObject("total");

    return totalObject.getDouble("gross") == expectedGross &&
        totalObject.getDouble("vat") == expectedVat;
  }

  private boolean verifyGenericField(String fieldName){
    for (Object j : jsonObject.getJSONArray("results")){
      JSONObject jso = (JSONObject)j;
      if (!jso.has(fieldName)){
        return false;
      }
    }

    return true;
  }

  public boolean verifyPriceFields() {
    return verifyGenericField("unit_price");
  }

  public boolean verifyDescriptionFields() {
    for (Object j : jsonObject.getJSONArray("results")){
      JSONObject jso = (JSONObject)j;
      if (!jso.has("description")){
        return false;
      } else {
        if (jso.getString("description").contains("\n")){
          return false;
        }
      }
    }

    return true;
  }

  public boolean verifyTitleFields() {
    return  verifyGenericField("title");
  }

  public boolean verifyCalories(ArrayList<String> productsWithoutCalories) {
    for (Object j : jsonObject.getJSONArray("results")){
      JSONObject jso = (JSONObject)j;
      if (!jso.has("kcal_per_100g")){
        if (!productsWithoutCalories.contains(jso.getString("title"))){
          System.err.println("product " + jso.getString("title") + " should have calories");
          return false;
        }
      }
    }

    return true;
  }
}
