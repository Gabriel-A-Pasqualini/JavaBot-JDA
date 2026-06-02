package bot.alexander.Commands;

import bot.alexander.alexander;
import bot.alexander.Apis.api;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.json.JSONObject;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AdviceCommands extends ListenerAdapter {

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("concelho")) return;  

        try {
            String jsonResponse = api.adviceDiscord();
            JSONObject obj = (new JSONObject(jsonResponse)).getJSONObject("slip");
            alexander.embed.setTitle("⚖ Your advice is: ");
            alexander.embed.addField("advice: ", obj.getString("advice"), true);
            alexander.embed.setColor(0xab0a1d);

            event.getChannel().sendTyping().queue();
            event.getHook().sendMessageEmbeds(alexander.embed.build()).queue();

            alexander.embed.clear();

        } catch (ArrayIndexOutOfBoundsException e) {
        }        
    }
}
