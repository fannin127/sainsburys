package sainsburys.Main;

import sainsburys.json.JsonBuilder;
import sainsburys.utils.Constants;
import sainsburys.utils.WebPageReader;

public class Main {

  public static void main(String[] args){
    JsonBuilder js = new JsonBuilder();

    System.out.println(js.getFullJsonData(Constants.BERRIES_URL));

  }
}
