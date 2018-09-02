package sainsburys;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 * JsonVerifier class
 * Used in the test steps to verify the JSON received from the JsonBuilder is correct
 */
public class JsonVerifier {
  JSONObject jsonObject;

  /**
   * Constructor Method
   * @param jsonObject - The JSONObject that needs verification
   */
  public JsonVerifier(JSONObject jsonObject){
    this.jsonObject = jsonObject;
  }

  /**
   * Verify that the total values(vat and gross) are correct
   * @param expectedGross - the expected gross value
   * @param expectedVat - the expected vat value
   * @return true if the total values equal the expected values, else false
   */
  public boolean verifyTotal(double expectedGross, double expectedVat){
    JSONObject totalObject = jsonObject.getJSONObject("total");

    return totalObject.getDouble("gross") == expectedGross &&
        totalObject.getDouble("vat") == expectedVat;
  }

  /**
   * A generic check if all objects in the JSON has a given field
   * @param fieldName field to check for
   * @return true if all objects have the field, false if one object does not
   */
  private boolean verifyGenericField(String fieldName){
    for (Object j : jsonObject.getJSONArray("results")){
      JSONObject jso = (JSONObject)j;
      if (!jso.has(fieldName)){
        return false;
      }
    }

    return true;
  }

  /**
   * Calls the generic field verifier to check for the unit_price field
   * @return true if all objects have a unit_price field, else false
   */
  public boolean verifyPriceFields() {
    return verifyGenericField("unit_price");
  }

  /**
   * Iterates over the json results array and checks that each object has a one line description
   * @return true if all objects in the array have a one line description, else false
   */
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

  /**
   * Calls the generic field verifier to check for the description field
   * @return true if all the objects have a description field, else false
   */
  public boolean verifyTitleFields() {
    return  verifyGenericField("title");
  }

  /**
   * Iterates over the Json array ensuring all objects that should have calorie information do
   * @param productsWithoutCalories an ArrayList<String> containing the titles of products that do
   *                                not have calorie information
   * @return true if all objects that should have calorie information do, else false
   */
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
