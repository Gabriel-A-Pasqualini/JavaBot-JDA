package bot.alexander.Commands;

import bot.alexander.alexander;
import bot.alexander.apis.song;
import org.json.JSONArray;
import org.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ArtistCommands extends ListenerAdapter {
    
    String prefix = alexander.prefix;

   @Override 
    public void onMessageReceived(MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();

        if (message.startsWith(prefix + "artist")) {
            try {
                String[] parts = message.split("artist", 2);
                if (parts.length < 2) {
                    event.getChannel().sendMessage("❌ Você precisa informar o nome do artista.").queue();
                    return;
                }

                String artist = parts[1].trim();

                String jsonResponse = song.searchBand(artist);
                JSONObject obj = new JSONObject(jsonResponse);

                JSONObject artists = obj.getJSONObject("artist");
                JSONArray genres = artists.getJSONArray("genre");

                JSONObject albums = artists.getJSONObject("albums");
                JSONArray item = albums.getJSONArray("item");

                JSONObject toplyrics = artists.getJSONObject("toplyrics");
                JSONArray songs = toplyrics.getJSONArray("item");

                String[] genresList = new String[genres.length()];
                for (int i = 0; i < genres.length(); i++) {
                    JSONObject aux = genres.getJSONObject(i);
                    genresList[i] = aux.getString("name");
                }

                String[] itemList = new String[item.length()];
                for (int i = 0; i < item.length(); i++) {
                    JSONObject aux = item.getJSONObject(i);
                    itemList[i] = aux.getString("desc") + " - " + aux.getString("year");
                }

                int len = Math.min(5, songs.length());
                String[] topmusics = new String[len];
                for (int i = 0; i < len; i++) {
                    JSONObject aux = songs.getJSONObject(i);
                    topmusics[i] = aux.getString("desc");
                }

                String img = artists.getString("pic_medium");
                String imgUrl = "https://www.vagalume.com.br" + img;

                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("Artist: " + artists.getString("desc"));
                info.addField("Genres: ", String.join(" / ", genresList), false);
                info.addField("Top " + len + " song's: ", String.join("\n", topmusics), false);
                info.addField("Albums: ", String.join("\n", itemList), false);
                info.addField("Spotify: ", "https://open.spotify.com/search/" + artist.replace(" ", "%20"), false);
                info.setThumbnail(imgUrl);
                info.setColor(0xab0a1d);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(info.build()).queue();

            } catch (Exception e) {
                System.out.println("Artist error 1: " + e);
                event.getChannel().sendMessage("❌ Ocorreu um erro ao buscar o artista.").queue();
            }
        }
    }    
}
