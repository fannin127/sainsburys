package sainsburys.utils;

import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.lang.StringBuffer;
import java.net.MalformedURLException;
public class WebPageReader {

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
            System.err.println("Excpetion thrown opening url stream");
            return null;
        }

        return buffer.toString();
    }




}