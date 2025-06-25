package bot.alexander.Commands;

import bot.alexander.alexander;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ImageGemCommands extends ListenerAdapter{
    
    String prefix = alexander.prefix;

    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (message.startsWith(prefix + "img")) {
            try {
                String[] parts = message.split("img", 2);
                if (parts.length < 2) {
                    System.out.println("Comando 'img' encontrado, mas sem parâmetro.");
                    return;
                } 

                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("📝 Desenhando: ");
                info.addField("Vou gerar sua imagem", "....", false);
                info.setColor(0xab0a1d);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(info.build()).queue();
                info.clear();
            } catch (Exception e) {
                System.out.println("Não foi possível gerar a imagem: " + e);
            }
        }
    }
}