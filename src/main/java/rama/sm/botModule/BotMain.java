package rama.sm.botModule;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.plugin.Plugin;
import rama.sm.StaffModule;


import static rama.sm.StaffModule.jda;

public class BotMain {

    private Plugin plugin = StaffModule.getPlugin(StaffModule.class);


    public BotMain(){
    }

    public void sendBotMessage(String player_name, String help_message){

        //EMBED
        EmbedBuilder eb = new EmbedBuilder();
        Boolean discord_integration = plugin.getConfig().getBoolean("discord-integration.enable");
        String title = plugin.getConfig().getString("discord-integration.notification-embed.title");
        String description = plugin.getConfig().getString("discord-integration.notification-embed.description").replaceAll("%player_name%", player_name);
        String help_message_name = plugin.getConfig().getString("discord-integration.notification-embed.help-message-field.name");
        String help_message_content = plugin.getConfig().getString("discord-integration.notification-embed.help-message-field.content").replace("%help-message%", help_message);
        Boolean inline = plugin.getConfig().getBoolean("discord-integration.notification-embed.help-message-field.inline");
        Boolean notify_role = plugin.getConfig().getBoolean("discord-integration.notify-role");
        int color = plugin.getConfig().getInt("discord-integration.notification-embed.color");
        //EMBED

        //IDS
        String channel_id = plugin.getConfig().getString("discord-integration.notification-channel-id");
        String role_id = plugin.getConfig().getString("discord-integration.notify-role-id");
        String guild_id = plugin.getConfig().getString("discord-integration.guild-id");
        //IDS

        if(!discord_integration){
            return;
        }

        eb.setTitle(title);
        eb.setColor(color);
        eb.setDescription(description);
        eb.addField(help_message_name, help_message_content, inline);
        TextChannel textChannel = jda.getGuildById(guild_id).getTextChannelById(channel_id);
        textChannel.sendMessageEmbeds(eb.build()).queue();
        if(notify_role) {
            Role role = jda.getRoleById(role_id);
            textChannel.sendMessage(role.getAsMention()).queue();
        }
    }
}
