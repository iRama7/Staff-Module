package rama.sm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

import static rama.sm.StaffModule.plugin;

public class MentionEvent implements Listener {

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent e){
        FileConfiguration config = plugin.getConfig();
        String mention_message = config.getString("mention-message");
        assert mention_message != null;
        if(e.getMessage().contains(mention_message)){
            playMention(plugin.online_staff_list(), e.getPlayer().getName());
        }
    }

    public void playMention(List<Player> staff_list, String who_pinged){
        String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("title.title"));
        String subtitle = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("title.subtitle").replaceAll("%player_name%", who_pinged));
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
