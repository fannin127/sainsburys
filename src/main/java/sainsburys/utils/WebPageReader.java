package sainsburys.utils;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.lang.StringBuffer;
import java.net.MalformedURLException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Class to connect to a webpage and download the HTML data
 */
public class WebPageReader {

    /**
     * connects to the given url and returns the html on the page as a string
     * @param urlString webpage to scrape
     * @return html of page as string
     */
    public String getHtmlFromUrl(String urlString){

        StringBuffer buffer = new StringBuffer();

        try {
            URL url = new URL(urlString);
            InputStream is = url.openStream();
            int ptr = 0;

            while ((ptr = is.read()) != -1) {
                buffer.append((char)ptr);
            }   
        } catch (MalformedURLException e){
            System.err.println("Exception thrown trying to reach URL");
            return null;
        } catch (IOException e){
            System.err.println("Exception thrown opening url stream");
            return null;
        }

        return buffer.toString();
    }


    /**
     * gets a Jsoup document for the given url in order to do parsing of the html
     * @param urlString url to scrape
     * @return Jsoup Document ready to parse
     */
    public Document getDocument(String urlString){
        return Jsoup.parse(getHtmlFromUrl(urlString));
    }



}