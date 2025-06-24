package bot.alexander.Commands;

import bot.alexander.alexander;
import bot.alexander.apis.song;

import javax.annotation.Nonnull;

import org.json.JSONArray;
import org.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ArtistCommands extends ListenerAdapter {

    String prefix = alexander.prefix;

    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();

        if (message.startsWith(prefix + "artist")) {
            try {
                String artist = message.split("artist")[1];
                String jsonResponse = song.searchBand(artist);

                JSONObject obj = new JSONObject(jsonResponse);

                EmbedBuilder info = new EmbedBuilder();

                JSONObject artists = obj.getJSONObject("artist");

                JSONArray genres = artists.getJSONArray("genre");

                JSONObject albums = artists.getJSONObject("albums");
                JSONArray item = albums.getJSONArray("item");

                JSONObject toplyrics = artists.getJSONObject("toplyrics");
                JSONArray songs = toplyrics.getJSONArray("item");

                String[] genresList = new String[genres.length()];
                String[] itemList = new String[item.length()];

                for (int i = 0; i < genres.length(); i++) {
                    JSONObject aux = (JSONObject) genres.get(i);

                    genresList[i] = aux.getString("name");
                }

                for (int i = 0; i < item.length(); i++) {
                    JSONObject aux = (JSONObject) item.get(i);
                    itemList[i] = aux.getString("desc") + " - " + aux.getString("year");
                }

                int len = songs.length() <= 5 ? songs.length() : 5;
                String[] topmusics = new String[len];

                for (int i = 0; i < len; i++) {
                    JSONObject aux = ((JSONObject) songs.get(i));
                    topmusics[i] = aux.getString("desc");
                }

                String img = artists.getString("pic_medium");
                String imgUrl = "https://www.vagalume.com.br" + img;

                info.setTitle("Artist: " + artists.getString("desc"));
                info.addField("Genres: ", String.join("/", genresList), false);
                info.addField("Top " + len + " song's: ", String.join("\n", topmusics), false);
                info.addField("Albums: ", String.join("\n", itemList), false);
                info.addField("Spotify: ", "https://open.spotify.com/search/" + artist.replace(" ", "%20"), false);
                info.setThumbnail(imgUrl); // setImage
                info.setColor(0xab0a1d);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(info.build()).queue();

            } catch (Exception e) {
                System.out.println("Artist Command - erro in try: " + e);
            }
        }
    }
}
