package bot.alexander.Commands;

import bot.alexander.alexander;
import bot.alexander.Helpers.ColorService;
import bot.alexander.Helpers.DebugMap;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class HelpCommands extends ListenerAdapter{

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("eu")) return;       
        
        DebugMap.DebugEvent(event.getUser().getAsTag(),event.getName());
            
        try {
        
            String userId = event.getUser().getId();
            String username = event.getUser().getName();
            String tag = event.getUser().getAsTag();
            String nick = event.isFromGuild() ? event.getMember().getEffectiveName() : "—";
            String serverId = event.getGuild().getId(); 
            String serverName = event.getGuild().getName(); 

            alexander.embed.setTitle("📋 Suas informações: ");
            alexander.embed.setThumbnail(event.getUser().getAvatarUrl());
            alexander.embed.addField(" ID","\u2022 " + userId, false);
            alexander.embed.addField(" Nome","\u2022 " + username, false);
            alexander.embed.addField(" Tag","\u2022 " + tag, false);
            alexander.embed.addField(" Nick","\u2022 " + nick, false);
            alexander.embed.addField(" ID Servidor","\u2022 " + serverId, false);
            alexander.embed.addField(" Nome Servidor","\u2022 " + serverName, false);
            alexander.embed.setColor(ColorService.convertColor("#e6eae1"));

            event.replyEmbeds(alexander.embed.build()).queue();

            alexander.embed.clear();

        } catch (ArrayIndexOutOfBoundsException e) {
        
            System.out.println("Help does't work!: "+e.getMessage());
        
            alexander.embed.setTitle("Suas informações: ");
            alexander.embed.addField("Erro: ",e.getMessage(), false);  
        
            event.getChannel().sendTyping().queue();
            event.getHook().sendMessageEmbeds(alexander.embed.build()).queue();
        }        
    }    
}