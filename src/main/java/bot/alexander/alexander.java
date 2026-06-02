package bot.alexander;

import java.nio.file.Files;
import java.nio.file.Paths;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import bot.alexander.service.DatabaseService;
import bot.alexander.service.CommandService;

public class alexander {
    
    public static EmbedBuilder embed = new EmbedBuilder();
     
    public static void main(String[] args) throws Exception {

        DatabaseService.init("./appSettings.json");

        String token = new String(Files.readAllBytes(Paths.get("./token.txt")));        

        JDABuilder builder = JDABuilder.createDefault(token,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.MESSAGE_CONTENT
        );           

        if (CommandService.loadEvent(builder)) {
            System.out.println("Eventos carregados");
        }

        JDA jda = builder.build();
        jda.awaitReady();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.customStatus("👾"));
        

        if (CommandService.regitraSlashCommands(jda)) {
            System.out.println("Comandos registrados");
        }   
        if (CommandService.regitraSlashCommandsGuild(jda)) {
            System.out.println("Comandos registrados");
        }      

        System.out.println("🤖 Bot online e comandos registrados!");
    }
}