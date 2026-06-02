package bot.alexander.Commands;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class RegistraCommands extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("registrar"))
            return;

        String userId = event.getUser().getId();

        TextInput inputName = TextInput.create(
                "nome",
                "Digite seu nome",
                TextInputStyle.SHORT)
            .setPlaceholder("Ex: Alexandre")
            .setMinLength(1)
            .setMaxLength(100)
            .setRequired(true)
            .build();

        Modal modal = Modal.create(
                "registro-modal",
                "📋 Registro | ID: " + userId)
            .addActionRow(inputName)
            .build();

        event.replyModal(modal).queue();
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {

        if (!event.getModalId().equals("registro-modal"))
            return;

        String nome = event.getValue("nome").getAsString();
        String discordId = event.getUser().getId();

        event.reply(
                "📝 Nome: `" + nome + "`\n" +
                "🆔 ID: `" + discordId + "`")
            .setEphemeral(true)
            .queue();
    }
}