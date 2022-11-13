package rama.sm.botModule;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static rama.sm.StaffModule.hex;
import static rama.sm.StaffModule.plugin;

public class StaffMsg {

    public void sendStaffMSG(String msg , Player s){
        String format = plugin.getConfig().getString("staff-msg.format");
        String final_format = hex(format.replaceAll("%staff_name%", s.getName()).replaceAll("%message%", msg));
        Sound sound = Sound.valueOf(plugin.getConfig().getString("staff-msg.sound"));
        float pitch = (float) plugin.getConfig().getInt("staff-msg.pitch");
        for(Player staff : plugin.online_staff_list()){
            staff.sendMessage(final_format);
            staff.playSound(staff.getLocation(), sound, 100, pitch);
        }
    }
}
