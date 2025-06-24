package bot.alexander.Commands;

import bot.alexander.alexander;
import bot.alexander.apis.myanimelistAip;
import javax.annotation.Nonnull;
import org.json.JSONArray;
import org.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AnimeCommands extends ListenerAdapter {

    String prefix = alexander.prefix;

    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        String message = event.getMessage().getContentRaw();
        if (message.startsWith(prefix + "anime")) {
            try {
                String title = message.split("anime")[1];
                String jsonResponse = myanimelistAip.searchAnime(title);
                JSONObject obj = new JSONObject(jsonResponse);
                JSONArray objaux = obj.getJSONArray("data");
                JSONObject f = objaux.getJSONObject(0);
                EmbedBuilder info = new EmbedBuilder();
                float score_float = f.getFloat("score");
                String score = String.valueOf(score_float);

                info.setTitle("🍣 🍜 Information about the Anime: " + f.getString("title") + "");
                info.addField("Score: ",score , true);
                info.addField("Type: ", f.getString("type"), true);
                float x2 = f.getFloat("episodes");
                String s2 = String.valueOf(x2);
                info.addField("Number of episodes: ", s2, true);
                //info.addField("Start date: ", f.getString("start_date").split("T")[0], true);
                //info.addField("End date: ", f.getString("end_date").split("T")[0], true);
                info.addField("Synopsis: ", f.getString("synopsis"), false);
                //info.setImage(f.getString("image_url")); // info.setImage or info.setThumbnail
                info.setColor(0xab0a1d);
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(info.build()).queue();

                info.clear();

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("AnimeCommand - Error: "+e);
            }
        }
    }
}
