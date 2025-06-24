package bot.alexander.apis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class myanimelistAip {
    
    //public static final String SEARCH_URL = "https://api.jikan.moe/v3/search/anime?q=title";
    public static final String SEARCH_URL = "https://api.jikan.moe/v4/anime?q=title";
    
    public static String sendGetRequest(String requestUrl){
        StringBuffer response = new StringBuffer();
       
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept","*/*");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while((line = buffer.readLine()) != null){
                response.append(line);
            }
            buffer.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            
            System.out.println("MyAnimeList API - URL mal formatada: "+e);
        } catch (IOException e) {
            
            System.out.println("MyAnimeList API - Falha de input: "+e);
        }


        return response.toString();
    }
    public static String searchAnime(String title){
        String requestUrl = SEARCH_URL.replace("title", title.trim().replace(" ", "%20"));
        return sendGetRequest(requestUrl);
    }

}
