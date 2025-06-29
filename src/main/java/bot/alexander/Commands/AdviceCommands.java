package bot.alexander.Commands;

import bot.alexander.alexander;
import bot.alexander.apis.api;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AdviceCommands extends ListenerAdapter {

    String prefix = alexander.prefix;

    public void MessageReceivedEvent(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(prefix + "advice")) {
        //if (event.getMessage()) {    
            try {
                String jsonResponse = api.adviceDiscord();

                JSONObject obj = (new JSONObject(jsonResponse)).getJSONObject("slip");

                alexander.embed.setTitle("⚖ Your advice is: ");
                alexander.embed.addField("advice: ", obj.getString("advice"), true);
                alexander.embed.setColor(0xab0a1d);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(alexander.embed.build()).queue();

                alexander.embed.clear();

            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
    }
}
