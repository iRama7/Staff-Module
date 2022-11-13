package rama.sm;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import static rama.sm.StaffModule.plugin;

public class PlaceholderExpansion extends me.clip.placeholderapi.expansion.PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "staff";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ImRama";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if(params.equalsIgnoreCase("online_count")) {
            return String.valueOf(plugin.online_staff_list().size());
        }

        if(params.equalsIgnoreCase("count")) {
            return String.valueOf(plugin.staff_list_names().size());
        }

        return null;
    }
}
