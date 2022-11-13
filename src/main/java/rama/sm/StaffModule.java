package rama.sm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StaffModule extends JavaPlugin {

    private File dataFile;
    private FileConfiguration data;
    public static StaffModule plugin;

    @Override
    public void onEnable() {
        plugin = this;
        createDataFile();
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new MentionEvent(), this);
        Bukkit.getServer().getPluginCommand("staff").setExecutor(new Commands());
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderExpansion().register();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileConfiguration getData() {
        return this.data;
    }
    public File getDataFile(){
        return this.dataFile;
    }

    private void createDataFile() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }

        data = new YamlConfiguration();
        try {
            data.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public List<Player> online_staff_list(){
        List<Player> list = new ArrayList<>();
        for(String name : data.getStringList("staff_list")){
            Player p = Bukkit.getPlayer(name);
            if(p == null){continue;}
            if(p.isOnline()){
                list.add(p);
            }
        }
        return list;
    }

    public List<String> staff_list_names(){
        return new ArrayList<>(data.getStringList("staff_list"));
    }

    public static String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }

            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
