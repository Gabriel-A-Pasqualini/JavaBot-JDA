package bot.alexander.Commands;

import bot.alexander.service.ColorService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpCommands extends ListenerAdapter{

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("eu")){

            event.deferReply().queue();

            try {
            
                String userId = event.getUser().getId();
                String username = event.getUser().getName();
                String tag = event.getUser().getAsTag();
                String nick = event.isFromGuild() ? event.getMember().getEffectiveName() : "—";
                String serverId = event.getGuild().getId(); 
                String serverName = event.getGuild().getName(); 

                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("📋 Suas informações: ");
                info.setThumbnail(event.getUser().getAvatarUrl());
                info.addField(" ID","\u2022 " + userId, false);
                info.addField(" Nome","\u2022 " + username, false);
                info.addField(" Tag","\u2022 " + tag, false);
                info.addField(" Nick","\u2022 " + nick, false);
                info.addField(" ID Servidor","\u2022 " + serverId, false);
                info.addField(" Nome Servidor","\u2022 " + serverName, false);
                info.setColor(ColorService.convertColor("#e6eae1"));

                event.getChannel().sendTyping().queue();
                event.getHook().sendMessageEmbeds(info.build()).queue();

                info.clear();
                    
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("help java não esta funcionando: "+e.getMessage());

                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("Suas informações: ");
                info.addField("Erro: ",e.getMessage(), false);  

                event.getChannel().sendTyping().queue();
                event.getHook().sendMessageEmbeds(info.build()).queue();
            }
        }
    }    
}