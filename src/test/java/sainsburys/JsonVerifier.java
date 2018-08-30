package sainsburys;


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

}
