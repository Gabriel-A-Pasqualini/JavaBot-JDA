package bot.alexander.Commands;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;
import com.google.gson.Gson;
import bot.alexander.Classes.SpotifyClasses.ArtistItem;
import bot.alexander.Classes.SpotifyClasses.SpotifySearch;
import bot.alexander.Classes.SpotifyClasses.TrackSearch;
import bot.alexander.apis.ApiSpotify;
import bot.alexander.apis.TokenSpotify.TokenSpotify;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import  bot.alexander.service.ColorService;

public class SpotifyCommands extends ListenerAdapter{
    
    final String SEARCH_URL = "https://accounts.spotify.com/api/token";
 
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("spotify")) return;

        String name = event.getOption("artista").getAsString();
        event.deferReply().queue();

        try {
            String token = new JSONObject(TokenSpotify.sendGetRequest(SEARCH_URL))
                            .getString("access_token");

            ArtistItem artist = fetchFirstArtist(token, name);
            if (artist == null) {
                event.getHook().sendMessage("❌ Artista não encontrado: " + name).queue();
                return;
            }

            List<String> topTracks = fetchTopTracks(token, artist.id, 10);

            EmbedBuilder eb = new EmbedBuilder()
            .setTitle("🎸   " + artist.name, "https://open.spotify.com/artist/" + artist.id)
            .setThumbnail(artist.images.get(0).url)
            .setFooter(artist.name, artist.images.get(0).url)
            .setDescription("Here some informations about "+ artist.name)
            .addField("Followers", String.valueOf(artist.followers.total), true)
            .addField("Genres", String.join(" | ", artist.genres), false)
            .addField("Top 10 Tracks :", "  \u2022 " + String.join("\n\u2022   ", topTracks), false)                                
            .setColor(ColorService.convertColor("#1DB954"));

            event.getHook().sendMessageEmbeds(eb.build()).queue();

        } catch (Exception ex) {
            event.getHook().sendMessage("❌ Erro ao buscar "+name+"").queue();
            ex.printStackTrace();
        }
    }

    private ArtistItem fetchFirstArtist(String token, String artistName) throws IOException, InterruptedException {
        String resp = ApiSpotify.searchSpotify(token, artistName);
        SpotifySearch search = new Gson().fromJson(resp, SpotifySearch.class);
        return (search.artists.items.isEmpty() ? null : search.artists.items.get(0));
    }

    private List<String> fetchTopTracks(String token, String artistId, int limit) throws IOException, InterruptedException {
        String resp = ApiSpotify.searchTracksSpotify(token, artistId);
        TrackSearch ts = new Gson().fromJson(resp, TrackSearch.class);
        return ts.tracks.stream().map(t -> t.name).limit(limit).collect(Collectors.toList());
    }       
}
