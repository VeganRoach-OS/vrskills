package base.plugin;

import base.listeners.PlayerBlockBreakListener;
import base.listeners.PlayerLoginListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 2mac
 */
public class StatHandler extends JavaPlugin
{
    public static final StatHandler thisPlugin = new StatHandler();
    public Map<String, Skill[]> playerSkills;
    public YamlConfiguration config, experience;
    private File configFile, experienceFile;

    @Override
    public void onEnable()
    {
        configFile = new File(getDataFolder(), "config.yml");
        experienceFile = new File(getDataFolder(), "experience.yml");
        config = new YamlConfiguration();
        experience = new YamlConfiguration();
        loadConfiguration();
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerBlockBreakListener(this), this);
        pm.registerEvents(new PlayerLoginListener(this), this);

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
        try
        {
            config.load(configFile);
            experience.load(experienceFile);
        } catch (IOException | InvalidConfigurationException e)
        {
            getLogger().severe("Unable to load configuration!");
            e.printStackTrace();
        }
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
