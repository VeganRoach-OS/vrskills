package base.plugin;

import base.listeners.PlayerBlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 2mac
 */
public class StatHandler extends JavaPlugin
{
    public static final StatHandler thisPlugin = new StatHandler();
    public Map<String, Skill[]> playerSkills;

    @Override
    public void onEnable()
    {
        loadConfiguration();
        Skill[] s;
        playerSkills = new HashMap<>();

        for(Player p : Bukkit.getOnlinePlayers())
        {
            s = new Skill[10];

            for(int i = 0; i < 10; i++)
            {
                s[i] = new Skill(SkillType.values()[i]);
            }

            playerSkills.put(p.getDisplayName(), s);
        }
    }

    // loads the default config from internal config.yml
    public void loadConfiguration()
    {
        thisPlugin.getConfig().options().copyDefaults(true);
        thisPlugin.saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        switch (command.getName().toLowerCase())
        {
            // all command work done with args to this command
            case "vrskills":
            case "skills":
                break;
        }
        return false;
    }

    @Override
    public void onDisable()
    {}
}
