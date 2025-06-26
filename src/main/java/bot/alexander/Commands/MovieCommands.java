package bot.alexander.Commands;

import bot.alexander.apis.movie;
import bot.alexander.alexander;
import org.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MovieCommands extends ListenerAdapter {

    String prefix = alexander.prefix;

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("filme")) return;
        
        event.deferReply().queue();
        String name = event.getOption("filme").getAsString();

        try {
            String jsonResponse = movie.searchMovieByTitle(name);
            JSONObject obj = new JSONObject(jsonResponse);
            EmbedBuilder info = new EmbedBuilder();

            info.setTitle("🎥 Information about the movie: " + obj.getString("Title") + "");
            info.addField("Genre: ", obj.getString("Genre"), false);
            info.addField("Year: ", obj.getString("Year"), false);
            info.addField("Released: ", obj.getString("Released"), false);
            info.addField("Country: ", obj.getString("Country"), false);
            info.addField("Director: ", obj.getString("Director"), false);
            info.addField("Runtime: ", obj.getString("Runtime"), false);
            info.addField("Actors: ", obj.getString("Actors"), false);
            info.addField("IMDB Rating: ", obj.getString("imdbRating"), false);
            info.addField("Awards: ", obj.getString("Awards"), false);
            info.setThumbnail(obj.getString("Poster")); // info.setImage or info.setThumbnail
            info.setColor(0xab0a1d);

            event.getChannel().sendTyping().queue();

            event.getHook().sendMessageEmbeds(info.build()).queue();

            info.clear();

        } catch (Exception ex) {
            event.getHook().sendMessage("❌ Ocorreu um erro ao buscar o filme.").queue();
            ex.printStackTrace();
        }      
    }
}