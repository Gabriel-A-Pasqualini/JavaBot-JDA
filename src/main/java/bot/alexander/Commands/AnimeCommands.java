package bot.alexander.Commands;

import bot.alexander.alexander;
import bot.alexander.apis.myanimelistAip;
import org.json.JSONArray;
import org.json.JSONObject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AnimeCommands extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("anime")) return;

        event.deferReply().queue();
        String name = event.getOption("anime").getAsString();

        try {
            String jsonResponse = myanimelistAip.searchAnime(name);
            JSONObject obj = new JSONObject(jsonResponse);
            JSONArray objaux = obj.getJSONArray("data");
            JSONObject f = objaux.getJSONObject(0);
            float score_float = f.getFloat("score");
            String score = String.valueOf(score_float);
            float x2 = f.getFloat("episodes");
            String s2 = String.valueOf(x2);

            alexander.embed.setTitle("🍣 🍜 Information about the Anime: " + f.getString("title") + "");
            alexander.embed.addField("Score: ",score , true);
            alexander.embed.addField("Type: ", f.getString("type"), true);
            alexander.embed.addField("Number of episodes: ", s2, true);
            alexander.embed.addField("Synopsis: ", f.getString("synopsis"), false);
            alexander.embed.setColor(0xab0a1d);
            
            event.getChannel().sendTyping().queue();
            event.getHook().sendMessageEmbeds(alexander.embed.build()).queue();

            alexander.embed.clear();

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("AnimeCommand - Error: "+e);
        }        
    }
}
