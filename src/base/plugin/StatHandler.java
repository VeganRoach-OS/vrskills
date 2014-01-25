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
    public static double expRatio;
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
        loadConfiguration((byte)0);
        expRatio = config.getDouble("experienceratio");
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
    public void loadConfiguration(byte tries)
    {
        try
        {
            config.load(configFile);
            experience.load(experienceFile);
        } catch (IOException | InvalidConfigurationException e)
        {
            tries++;
            if (tries > 3)
            {
                getLogger().severe("After " + tries + " attempts, could not generate config files.");
                e.printStackTrace();
            } else
            {
                getLogger().info("Configuration files not found. Generating...");
                writeDefaults(tries);
            }
        }
    }

    public void writeDefaults(byte tries)
    {
        if (!configFile.exists())
        {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        if (!experienceFile.exists())
        {
            experienceFile.getParentFile().mkdirs();
            saveResource("experience.yml", false);
        }

        loadConfiguration(tries);
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
