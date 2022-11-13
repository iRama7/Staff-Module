package rama.sm;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import rama.sm.botModule.BotMain;
import rama.sm.botModule.StaffMsg;

import java.util.List;

import static rama.sm.StaffModule.hex;
import static rama.sm.StaffModule.plugin;

public class MentionEvent implements Listener {

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent e){
        FileConfiguration config = plugin.getConfig();
        String mention_message = config.getString("mention-message");
        String no_help_message = config.getString("no-help-message");
        assert mention_message != null;
        if(e.getMessage().contains(mention_message)){
            if(plugin.online_staff_list().isEmpty()){
                String no_staff_message = hex(plugin.getConfig().getString("no-staff-message"));
                e.getPlayer().sendMessage(no_staff_message);
                BotMain bm = new BotMain();
                String player_name = e.getPlayer().getName();
                String message = e.getMessage();
                String help_message = message.replaceAll(mention_message, "");
                if(help_message.equals("")){
                    help_message = no_help_message;
                }
                bm.sendNoStaffMessage(player_name, help_message);
            }else {
                String message = hex(plugin.getConfig().getString("message"));
                e.getPlayer().sendMessage(message.replaceAll("%staff_count%", String.valueOf(plugin.online_staff_list().size())));
                playMention(plugin.online_staff_list(), e.getPlayer().getName());
            }
            return;
        }
        String firstChar = String.valueOf(e.getMessage().charAt(0));
        String staffMsgChar = plugin.getConfig().getString("staff-msg.prefix");
        if(firstChar.equals(staffMsgChar) && plugin.online_staff_list().contains(e.getPlayer())){
            StaffMsg sm = new StaffMsg();
            sm.sendStaffMSG(e.getMessage().replaceFirst(staffMsgChar, ""), e.getPlayer());
            BotMain bm = new BotMain();
            bm.sendTranscript(e.getPlayer().getName(), e.getMessage().replaceFirst(staffMsgChar, ""));
            e.setCancelled(true);
        }


    }

    public void playMention(List<Player> staff_list, String who_pinged){
        String title = hex(plugin.getConfig().getString("title.title"));
        String subtitle = hex(plugin.getConfig().getString("title.subtitle").replaceAll("%player_name%", who_pinged));
        int fadeIn = plugin.getConfig().getInt("title.fadeIn");
        int stay = plugin.getConfig().getInt("title.stay");
        int fadeOut = plugin.getConfig().getInt("title.fadeOut");
        Sound sound = Sound.valueOf(plugin.getConfig().getString("sound.name"));
        float pitch = (float) plugin.getConfig().getInt("sound.pitch");
        for(Player staff : staff_list){
            staff.playSound(staff.getLocation(), sound, 100F, pitch);
            staff.sendTitle(title.replaceAll("%staff_name%", staff.getName()), subtitle, fadeIn, stay, fadeOut);
        }

    }
}
