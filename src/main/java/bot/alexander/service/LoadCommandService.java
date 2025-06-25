package bot.alexander.service;
import bot.alexander.Commands.AdviceCommands;
import bot.alexander.Commands.AnimeCommands;
import bot.alexander.Commands.ArtistCommands;
import bot.alexander.Commands.CommandConfig;
import bot.alexander.Commands.HelpCommands;
import bot.alexander.Commands.HippocratesCommands;
import bot.alexander.Commands.ImageGemCommands;
import bot.alexander.Commands.MovieCommands;
import bot.alexander.Commands.PingCommands;
import bot.alexander.Commands.SpotifyCommands;
import bot.alexander.Commands.YugiohCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class LoadCommandService {

    public static boolean regitraSlashCommandsGuild(JDA jda){
        try{
            for (Guild guild : jda.getGuilds()) {
                guild.updateCommands()
                    .addCommands(
                        Commands.slash("ping", "Responde com pong"),
                        Commands.slash("spotify", "Busca artista no Spotify")
                            .addOption(OptionType.STRING, "artista", "Nome do artista", true)
                    )
                    .queue();
                System.out.println("Registrado em: " + guild.getId());
            }                                         
            return true;
        }
        catch(Exception e){
            System.out.println("Erro: "+e);
            return false;
        }        
    }      

    public static boolean regitraSlashCommands(JDA jda){
        try{
            jda.upsertCommand(
                Commands.slash("ping", "Responde com pong")         
            ).queue();

            jda.upsertCommand(
                Commands.slash("spotify", "Busca artista no Spotify")
                    .addOption(OptionType.STRING, "artista", "Nome do artista", true)          
            ).queue();                                         
            return true;
        }
        catch(Exception e){
            System.out.println("Erro: "+e);
            return false;
        }        
    }     

    public static boolean loadEvent(JDABuilder builder){
        try{
            builder.addEventListeners(new SpotifyCommands());
            builder.addEventListeners(new ArtistCommands());
            builder.addEventListeners(new HelpCommands());
            builder.addEventListeners(new AdviceCommands());
            builder.addEventListeners(new MovieCommands());
            builder.addEventListeners(new AnimeCommands());
            builder.addEventListeners(new CommandConfig());
            builder.addEventListeners(new YugiohCommand());
            builder.addEventListeners(new HippocratesCommands());
            builder.addEventListeners(new ImageGemCommands());
            builder.addEventListeners(new PingCommands());            
                        
            return true;
        }
        catch(Exception e){
            System.out.println("Erro: "+e);
            return false;
        }        
    }    
}
