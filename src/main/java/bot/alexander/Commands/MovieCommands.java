package bot.alexander.Commands;

import bot.alexander.alexander;
import bot.alexander.Apis.movie;

import org.json.JSONObject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MovieCommands extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("filme")) return;
        
        event.deferReply().queue();
        String name = event.getOption("filme").getAsString();

        try {
            String jsonResponse = movie.searchMovieByTitle(name);
            JSONObject obj = new JSONObject(jsonResponse);

            alexander.embed.setTitle("🎥 Information about the movie: " + obj.getString("Title") + "");
            alexander.embed.addField("Genre: ", obj.getString("Genre"), false);
            alexander.embed.addField("Year: ", obj.getString("Year"), false);
            alexander.embed.addField("Released: ", obj.getString("Released"), false);
            alexander.embed.addField("Country: ", obj.getString("Country"), false);
            alexander.embed.addField("Director: ", obj.getString("Director"), false);
            alexander.embed.addField("Runtime: ", obj.getString("Runtime"), false);
            alexander.embed.addField("Actors: ", obj.getString("Actors"), false);
            alexander.embed.addField("IMDB Rating: ", obj.getString("imdbRating"), false);
            alexander.embed.addField("Awards: ", obj.getString("Awards"), false);
            alexander.embed.setThumbnail(obj.getString("Poster")); // info.setImage or info.setThumbnail
            alexander.embed.setColor(0xab0a1d);

            event.getChannel().sendTyping().queue();
            event.getHook().sendMessageEmbeds(alexander.embed.build()).queue();

            alexander.embed.clear();

        } catch (Exception ex) {
            event.getHook().sendMessage("❌ Ocorreu um erro ao buscar o filme.").queue();
            ex.printStackTrace();
        }      
    }
}