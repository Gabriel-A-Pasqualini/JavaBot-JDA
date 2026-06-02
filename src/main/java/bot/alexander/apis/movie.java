package bot.alexander.Apis;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class movie {

    public static final String SEARCH_URL = "http://www.omdbapi.com/?apikey=943e3a95&t=title&type=movie";
    
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
            
            System.out.println("URL mal formatada");
        } catch (IOException e) {
            
            System.out.println("Falha de input");
        }
        return response.toString();
    }
    public static String searchMovieByTitle(String title){
        String requestUrl = SEARCH_URL.replace("title", title.trim().replace(" ", "%20"));
        return sendGetRequest(requestUrl);
    }
}


