package bot.alexander.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PermissionService {

    public static boolean validaPermissaoUsuario(String discordId, String serverId) {
        String sql = "SELECT ies_permiss_comando FROM usuario_servidores " +
                     "WHERE id_usu_disc = ? AND id_serv_disc = ?";

        try (Connection conn = DatabaseService.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, Long.parseLong(discordId));
            ps.setLong(2, Long.parseLong(serverId));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boolean hasPerm = rs.getBoolean("ies_permiss_comando");
                    return hasPerm;
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao validar permissão do usuário: " + e);
            return false;
        }
    }
}
