package bot.alexander.Commands;

import bot.alexander.alexander;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ImageGemCommands extends ListenerAdapter{
    
    String prefix = alexander.prefix;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {


        String message = event.getMessage().getContentRaw();
        String image = message.split("img")[1];        
        if (message.startsWith(prefix + "img" +image)) {
            try {
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("📝 Desenhando: ");
                info.addField("Vou gerar sua imagem ", "....", false);
                info.setColor(0xab0a1d);

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessageEmbeds(info.build()).complete();
                info.clear();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Não foi possivel gerar a imagem: "+e);
            }
        }
    }
}