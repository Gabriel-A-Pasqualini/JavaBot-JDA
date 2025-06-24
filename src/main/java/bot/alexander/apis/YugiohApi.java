package bot.alexander.apis;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YugiohApi {
    
    static OkHttpClient client = new OkHttpClient();

    public static String searchCard(String card) throws IOException {
        
        String requestUrl = "https://db.ygoprodeck.com/api/v7/cardinfo.php?name="+card; 


        Request request = new Request.Builder()
            .addHeader("Authorization", "Bearer ")    
            .url(requestUrl)
            .get()
            .build();

        System.out.println(request);        
    try (Response response = client.newCall(request).execute()) {
        return response.body().string();        
        }
    } 
}
