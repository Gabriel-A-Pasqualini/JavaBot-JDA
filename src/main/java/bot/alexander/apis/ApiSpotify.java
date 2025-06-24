package bot.alexander.apis;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiSpotify {
    
    static OkHttpClient client = new OkHttpClient();

    public static String searchSpotify(String token, String name) throws IOException {
        
        String requestUrl = "https://api.spotify.com/v1/search?q="+name+"&type=artist&market=BR"; 


        Request request = new Request.Builder()
            .addHeader("Authorization", "Bearer "+token)    
            .url(requestUrl)
            .get()
            .build();

    try (Response response = client.newCall(request).execute()) {
        return response.body().string();        
        }
    } 

    public static String searchTracksSpotify(String token, String id) throws IOException {
        
        String requestUrl = "https://api.spotify.com/v1/artists/"+id+"/top-tracks?market=BR"; 
        
        Request request = new Request.Builder()
            .addHeader("Authorization", "Bearer "+token)    
            .url(requestUrl)
            .get()
            .build();

    try (Response response = client.newCall(request).execute()) {
        return response.body().string();        
        }
    } 
}
