package bot.alexander.Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class RegistraCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("registrar")) return;

        String userId = event.getUser().getId();

        TextInput inputName = TextInput.create("nome", "Digite seu nome", TextInputStyle.SHORT)
            .setPlaceholder("Ex: Alexandre")
            .setMinLength(1)
            .setMaxLength(100)
            .setRequired(true)
            .build();

        TextInput inputId = TextInput.create("discordId", "Seu ID Discord", TextInputStyle.SHORT)
            .setValue(userId)     // já vem preenchido
            .setRequired(false)   // pode ser ignorado
            .build();

        Modal modal = Modal.create("registro-modal", "📋 Registro")
            .addActionRow(inputName)
            .addActionRow(inputId)
            .build();

        event.replyModal(modal).queue();
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent e) {
        if (!e.getModalId().equals("registro-modal")) return;

        String nome = e.getValue("nome").getAsString();
        String discordId = e.getValue("discordId").getAsString();

        e.reply("📝 Nome: `" + nome + "`\n🆔 ID: `" + discordId + "`")
         .setEphemeral(true)
         .queue();
    }
}
