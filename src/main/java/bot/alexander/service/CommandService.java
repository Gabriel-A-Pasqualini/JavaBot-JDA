package bot.alexander.service;
import bot.alexander.Commands.AdviceCommands;
import bot.alexander.Commands.AnimeCommands;
import bot.alexander.Commands.ArtistCommands;
import bot.alexander.Commands.CommandConfig;
import bot.alexander.Commands.HelpCommands;
import bot.alexander.Commands.MovieCommands;
import bot.alexander.Commands.SpotifyCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class CommandService {

    public static boolean regitraSlashCommandsGuild(JDA jda){
        try{
            for (Guild guild : jda.getGuilds()) {
                guild.updateCommands()
                    .addCommands(
                        Commands.slash("spotify", "Busca artista no Spotify")
                            .addOption(OptionType.STRING, "artista", "Nome do artista", true),
                        Commands.slash("34", "Voce saber o que busca aqui")
                            .addOption(OptionType.STRING, "personagem", "Nome do personagem", true),
                        Commands.slash("artist", "Busco seu artista no Vagalume")     
                            .addOption(OptionType.STRING, "artista", "Nome do artista", true),
                        Commands.slash("filme", "Busco seu sobre seu filme")     
                            .addOption(OptionType.STRING, "filme", "Nome do filme (em ingles)", true),    
                        Commands.slash("eu", "Minhas informações")                                                                                                              
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
                Commands.slash("spotify", "Busca seu artista no Spotify")
                    .addOption(OptionType.STRING, "artista", "Nome do artista", true)          
            ).queue(); 
            
            jda.upsertCommand(
                Commands.slash("34", "Voce saber o que busca aqui")     
                    .addOption(OptionType.STRING, "personagem", "Nome do personagem", true) 
                    .setNSFW(true)    
            ).queue();    
            
            jda.upsertCommand(
                Commands.slash("artista", "Busco deu artista no Vagalume")     
                    .addOption(OptionType.STRING, "artista", "Nome do artista", true)     
            ).queue();    
            
            jda.upsertCommand(
                Commands.slash("filme", "Busco seu sobre seu filme")     
                    .addOption(OptionType.STRING, "filme", "Nome do filme (em ingles)", true)                        
            ).queue();    
            
            jda.upsertCommand(
                Commands.slash("eu", "Minhas informações")                       
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
                        
            return true;
        }
        catch(Exception e){
            System.out.println("Erro: "+e);
            return false;
        }        
    }    
}
