package base.plugin;

import base.listeners.PlayerBlockBreakListener;
import base.listeners.PlayerLoginListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public static double globalExpRatio;
    public static double[] expRatios;
    public Map<String, Skill[]> playerSkills;
    public YamlConfiguration config, experience;
    private File configFile, experienceFile;
    private final String[] RATIOS = {"archeryExperienceRatio", "axesExperienceRatio",
                                     "excavationExperienceRatio", "fishingExperienceRatio",
                                     "herbalExperienceRatio", "miningExperienceRatio",
                                     "swordplayExperienceRatio", "tamingExperienceRatio",
                                     "unarmedCombatExperienceRatio", "woodcuttingExperienceRatio"};

    @Override
    public void onEnable()
    {
        expRatios = new double[RATIOS.length];
        configFile = new File(getDataFolder(), "config.yml");
        experienceFile = new File(getDataFolder(), "experience.yml");
        config = new YamlConfiguration();
        experience = new YamlConfiguration();
        loadConfiguration((byte)0);

        globalExpRatio = config.getDouble("globalExperienceRatio");

        for(int i = 0; i < expRatios.length; i++)
        {
            expRatios[i] = config.getDouble(RATIOS[i]);
        }

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerBlockBreakListener(this), this);
        pm.registerEvents(new PlayerLoginListener(this), this);

        Skill[] skills;
        playerSkills = new HashMap<>();

        for(Player p : Bukkit.getOnlinePlayers())
        {
            skills = new Skill[SkillType.values().length];

            for(int i = 0; i < SkillType.values().length; i++)
            {
                skills[i] = new Skill(SkillType.values()[i]);
            }

            playerSkills.put(p.getDisplayName(), skills);
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
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            boolean argFault = false;
            switch (command.getName().toLowerCase())
            {
                // all command work done with args to this command
                case "vrskills":
                case "skills":
                    if (args.length == 0)
                    {
                        argFault = true;
                        break;
                    }
                    switch (args[0])
                    {
                        default:
                        case "help":
                            printHelp(player);
                            break;
                        case "addxp":
                        case "addexp":
                            if (args.length == 3 || args.length == 4)
                            {
                                String skill = "";
                                for (SkillType type : SkillType.values())
                                {
                                    if (args[1].equalsIgnoreCase(type.toString()))
                                        skill = type.toString().toLowerCase();
                                }
                                if (skill.equals(""))
                                {
                                    argFault = true;
                                }else
                                {
                                    if (args.length == 3)
                                    {
                                        for (Skill s : playerSkills.get(player.getName()))
                                        {
                                            if (s.getType().toString().equalsIgnoreCase(skill))
                                                s.addExp(Integer.parseInt(args[2]), player.getName());
                                        }
                                    }else
                                    {
                                        for (Skill s : playerSkills.get(args[3]))
                                        {
                                            if (s.getType().toString().equalsIgnoreCase(skill))
                                                s.addExp(Integer.parseInt(args[2]), args[3]);
                                        }
                                    }

                                    player.sendMessage("Added " + args[2] + " to " + (args.length == 4 ? args[3] + "'s " : "your ") + skill + " skill.");
                                }
                            }

                            if (argFault)
                            {
                                player.sendMessage(ChatColor.RED + "Usage: /vrskills addexp <skill> <amount> [player]");
                                return false;
                            }
                            return true;
                        case "addlevel":
                        case "addlvl":
                            if (args.length == 3 || args.length == 4)
                            {
                                String skill = "";
                                for (SkillType type : SkillType.values())
                                {
                                    if (args[1].equalsIgnoreCase(type.toString()))
                                        skill = type.toString().toLowerCase();
                                }
                                if (skill.equals(""))
                                {
                                    argFault = true;
                                }else
                                {
                                    if (args.length == 3)
                                    {
                                        for (Skill s : playerSkills.get(player.getName()))
                                        {
                                            if (s.getType().toString().equalsIgnoreCase(skill))
                                                s.setLevel((byte) (Byte.parseByte(args[2]) + s.getCurrentLevel()));
                                        }
                                    }else
                                    {
                                        for (Skill s : playerSkills.get(args[3]))
                                        {
                                            if (s.getType().toString().equalsIgnoreCase(skill))
                                                s.setLevel((byte) (Byte.parseByte(args[2]) + s.getCurrentLevel()));
                                        }
                                    }

                                    player.sendMessage("Added " + args[2] + (Byte.parseByte(args[2]) == (byte)1 ? " level" : " levels") + " to " + (args.length == 4 ? args[3] + "'s " : "your ") + skill + " skill.");
                                }
                            }

                            if (argFault)
                            {
                                player.sendMessage(ChatColor.RED + "Usage: /vrskills addlevel <skill> <amount> [player]");
                                return false;
                            }
                            return true;
                        case "setlevel":
                        case "setlvl":
                            if (args.length == 3 || args.length == 4)
                            {
                                String skill = "";
                                for (SkillType type : SkillType.values())
                                {
                                    if (args[1].equalsIgnoreCase(type.toString()))
                                        skill = type.toString().toLowerCase();
                                }
                                if (skill.equals(""))
                                {
                                    argFault = true;
                                }else
                                {
                                    if (args.length == 3)
                                    {
                                        for (Skill s : playerSkills.get(player.getName()))
                                        {
                                            if (s.getType().toString().equalsIgnoreCase(skill))
                                                s.setLevel(Byte.parseByte(args[2]));
                                        }
                                    }else
                                    {
                                        for (Skill s : playerSkills.get(args[3]))
                                        {
                                            if (s.getType().toString().equalsIgnoreCase(skill))
                                                s.setLevel(Byte.parseByte(args[2]));
                                        }
                                    }

                                    player.sendMessage("Set " + (args.length == 4 ? args[3] + "'s " : "your ") + skill + " skill level to " + args[2] + ".");
                                }
                            }

                            if (argFault)
                            {
                                player.sendMessage(ChatColor.RED + "Usage: /vrskills setlevel <skill> <level> [player]");
                                return false;
                            }
                            return true;
                    }
                    return true;
            }

            if (argFault) printHelp(player);
            return false;
        }else
        {
            // todo: console commands
        }

        return false;
    }

    public void printHelp(Player p)
    {
        // todo: display command reference
    }

    @Override
    public void onDisable()
    {}
}
