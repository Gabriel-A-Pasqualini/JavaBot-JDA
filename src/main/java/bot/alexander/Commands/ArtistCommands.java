package bot.alexander.Commands;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import bot.alexander.Classes.ArtistClasses.Artist;
import bot.alexander.Classes.ArtistClasses.ArtistResponse;
import bot.alexander.apis.song;
import bot.alexander.service.ColorService;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class ArtistCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("artista")) return;
        
        event.deferReply().queue();
        String name = event.getOption("artista").getAsString();

        try {
            Artist artist = fetchArtist(name);
            if (artist == null) {
                event.getHook().sendMessage("❌ Artista não encontrado: " + name).queue();
                return;
            }

            List<String> genres = listGenres(artist);
            List<String> albums = listAlbumDesc(artist);
            List<String> songs = listTopSongs(artist);

            String imgUrl = "https://www.vagalume.com.br" + artist.pic_medium;

            EmbedBuilder eb = new EmbedBuilder()
                .setTitle("Artist: " + artist.desc)
                .setDescription("https://www.vagalume.com.br/"+artist.desc.toLowerCase().replace(" ", "-")+"/")
                .addField("Genres", String.join(" | ", genres), false)
                .addField("Top " + songs.size() + " songs",  "  \u2022 " + String.join("\n\u2022   ", songs), false)
                .addField("10 ultimos albums", "  \u2022 " + String.join("\n\u2022   ", albums), false)
                .addField("Spotify", "https://open.spotify.com/search/" + artist.desc.replace(" ", "%20"), false)
                .setThumbnail(imgUrl)
                .setColor(ColorService.convertColor("#CCFF00"));

            event.getHook().sendMessageEmbeds(eb.build()).queue();

        } catch (Exception ex) {
            event.getHook().sendMessage("❌ Ocorreu um erro ao buscar o artista.").queue();
            ex.printStackTrace();
        }
    }   

    private Artist fetchArtist(String artistName) throws IOException {
        String json = song.searchBand(artistName);
        ArtistResponse resp = new Gson().fromJson(json, ArtistResponse.class);
        return resp.artist;
    }

    private List<String> listAlbumDesc(Artist artist) {
        return artist.albums.item.stream()
            .map(a -> a.desc + " - " + a.year)
                        .limit(10)
                        .collect(Collectors.toList());
    }

    private List<String> listTopSongs(Artist artist) {
        return artist.toplyrics.item.stream()
            .limit(5)
            .map(m -> m.desc)
            .collect(Collectors.toList());
    }

    private List<String> listGenres(Artist artist) {
        return artist.genre.stream()
            .map(g -> g.name)
            .collect(Collectors.toList());
    }
}

