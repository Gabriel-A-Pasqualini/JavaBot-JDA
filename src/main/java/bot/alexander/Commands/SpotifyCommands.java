package bot.alexander.Commands;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.json.JSONArray;
import org.json.JSONObject;
import bot.alexander.alexander;
import bot.alexander.apis.ApiSpotify;
import bot.alexander.apis.TokenSpotify.TokenSpotify;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SpotifyCommands extends ListenerAdapter{
    
    String prefix = alexander.prefix;

    //@Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();
        String name = message.split("spotify")[1];
        
        if (message.equalsIgnoreCase(prefix + "spotify" + name)) {  
            System.out.println("start Spotify");          
            try {

                final String SEARCH_URL = "https://accounts.spotify.com/api/token";
                
                String tokenResponse = TokenSpotify.sendGetRequest(SEARCH_URL);

                String token = (new JSONObject(tokenResponse)).getString("access_token");

                String spotifyResponse = ApiSpotify.searchSpotify(token, name);
     
                JSONObject info = new JSONObject(spotifyResponse);

                JSONObject artist = info.getJSONObject("artists");

                JSONArray items = artist.getJSONArray("items");

                JSONObject infos = items.getJSONObject(0);

                JSONArray imagem = infos.getJSONArray("images");
                
                JSONObject imagem_640 = imagem.getJSONObject(0);
                String url_imagem_640 = imagem_640.getString("url");
                //JSONObject imagem_320 = imagem.getJSONObject(1);
                //JSONObject imagem_160 = imagem.getJSONObject(2);

                String id = infos.getString("id");

                String trackeResponse = ApiSpotify.searchTracksSpotify(token, id);
                JSONObject auxTracke = new JSONObject(trackeResponse);
               
                JSONArray tracks = auxTracke.getJSONArray("tracks");
                String[] tracksList = new String[tracks.length()];
                for(int i = 0; i < tracks.length(); i++){

                    JSONObject name_album = tracks.getJSONObject(i);
                    String name_album_string = name_album.getString("name");

                    tracksList[i] = name_album_string;
                }
               
                JSONArray genres = infos.getJSONArray("genres");
                String[] genresList = new String[genres.length()];              
                for(int i = 0; i < genres.length(); i++){

                    genresList[i] = genres.getString(i);
                }

                JSONObject followers = infos.getJSONObject("followers");
                Integer total = followers.getInt("total".toString());

                String newname = infos.getString("name");

                EmbedBuilder body = new EmbedBuilder();
                body.setTitle("🎸 Information about the Artist: " +newname+ "");             
                body.addField("Followers: ", total.toString(), true);
                body.addField("Genres: ", String.join(" / ",genresList), false);
                body.addField("Top 10 Tracks: ", String.join("\n",tracksList), false);
                body.addField("Link: ", "https://open.spotify.com/artist/"+id, false);
                body.setThumbnail(url_imagem_640); // info.setImage or info.setThumbnail
                body.setColor(0xab0a1d);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(body.build()).queue();

                body.clear();

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Spotify command erro: "+e);
            
            } catch (IOException e) {
                
                e.printStackTrace();
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
    }
}
