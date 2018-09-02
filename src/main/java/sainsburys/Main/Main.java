package sainsburys.Main;

import sainsburys.json.JsonBuilder;
import sainsburys.utils.Constants;

/**
 * Simple main class to hold the main method
 */
public class Main {

  /**
   * The main method, entry point into the application, Simply calls the JSONBuilder with the
   * Berries URL and prints the result
   *
   * @param args there are no required arguments
   */
  public static void main(String[] args) {
    JsonBuilder js = new JsonBuilder();

    System.out.println(js.getFullJsonData(Constants.BERRIES_URL).toString(2));
  }
}
