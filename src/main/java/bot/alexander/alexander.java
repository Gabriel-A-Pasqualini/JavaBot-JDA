package bot.alexander;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import bot.alexander.service.LoadCommandService;

public class alexander {
    
    public static String prefix="?";
     
    public static void main(String[] args) throws Exception {

        String token = new String(Files.readAllBytes(Paths.get("./token.txt")));

        JDABuilder builder = JDABuilder.createDefault(token,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.MESSAGE_CONTENT
        );

        if (LoadCommandService.loadEvent(builder)) {
            System.out.println("➡️ Eventos carregados");
        }

        JDA jda = builder.build();
        jda.awaitReady();

        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.playing("Visual Studio Code"));

        if (LoadCommandService.regitraSlashCommands(jda)) {
            System.out.println("➡️ Comandos registrados");
        }   
        if (LoadCommandService.regitraSlashCommandsGuild(jda)) {
            System.out.println("➡️ Comandos registrados");
        }      

        System.out.println("🤖 Bot online e comandos registrados!");
    }
}