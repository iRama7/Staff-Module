package rama.sm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


import java.io.IOException;
import java.util.List;

import static rama.sm.StaffModule.plugin;

public class Commands implements CommandExecutor {
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
                    sender.sendMessage("Añadiste a " + player_name + " a la lista de staff.");
                    staff_list.add(player_name);
                    plugin.getData().set("staff_list", staff_list);
                    try {
                        plugin.getData().save(plugin.getDataFile());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    sender.sendMessage(player_name + " ya pertenece a la lista.");
                }
            }else if(args[0].equals("remove")){
                String player_name = args[1];
                if (staff_list.contains(player_name)) {
                    sender.sendMessage("Removiste a " + player_name + " de la lista de staff.");
                    staff_list.remove(player_name);
                    plugin.getData().set("staff_list", staff_list);
                    try {
                        plugin.getData().save(plugin.getDataFile());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    sender.sendMessage(player_name + " no pertenece a la lista.");
                }
            }else if(args[0].equals("reload")){
                plugin.reloadConfig();
            }

        }
        return false;
    }
}
