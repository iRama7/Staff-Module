package rama.sm;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static rama.sm.StaffModule.hex;
import static rama.sm.StaffModule.plugin;

public class Commands implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("staff.adder")) {
            if (args.length == 0) {
                sender.sendMessage("Utiliza /staff add <nick>");
                return false;
            }
            List<String> staff_list = plugin.staff_list_names();
            if(args[0].equals("add")) {
                String player_name = args[1];
                if (!staff_list.contains(player_name)) {
                    sender.sendMessage(hex(plugin.getConfig().getString("added-staff")).replaceAll("%player_name%", player_name));
                    staff_list.add(player_name);
                    plugin.getData().set("staff_list", staff_list);
                    try {
                        plugin.getData().save(plugin.getDataFile());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    sender.sendMessage(hex(plugin.getConfig().getString("already-staff")).replaceAll("%player_name%", player_name));
                }
            }else if(args[0].equals("remove")){
                String player_name = args[1];
                if (staff_list.contains(player_name)) {
                    sender.sendMessage(hex(plugin.getConfig().getString("removed-staff").replaceAll("%player_name", player_name)));
                    staff_list.remove(player_name);
                    plugin.getData().set("staff_list", staff_list);
                    try {
                        plugin.getData().save(plugin.getDataFile());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    sender.sendMessage(hex(plugin.getConfig().getString("cannot-remove").replaceAll("%player_name%", player_name)));
                }
            }else if(args[0].equals("reload")){
                sender.sendMessage(hex("&eReloaded Staff Module config."));
                plugin.reloadConfig();
            }
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (sender.hasPermission("staff.adder")) {
            if (args.length == 1) {
                commands.add("add <player_name>");
                commands.add("remove <player_name>");
                commands.add("reload");
                StringUtil.copyPartialMatches(args[0], commands, completions);
            }
        }
        Collections.sort(completions);
        return completions;
    }
}
