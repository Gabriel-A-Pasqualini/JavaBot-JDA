package bot.alexander.apis.TokenSpotify;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TokenSpotify {

    public static String sendGetRequest(String requestUrl) throws IOException, InterruptedException {

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
        .add("grant_type", "client_credentials")
        .build();

        Request request = new Request.Builder()
            .url(requestUrl)
            .addHeader("Authorization", "Basic OWRmZWNjZjFjOTdjNDliMmJjOTc0YjdkNTJiMDg4YWM6MWU1MDU2MDBiZTIwNGRjZDlhYzhlODhkNWY1MjNkZmQ=")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .post(formBody)
            .build();
 
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            
            System.out.println("Response API, new Spotify Token\n"+responseBody);
            return responseBody;
        }
        catch (IOException e) {
               return "Erro: "+e;
            
        }         
    }
}

