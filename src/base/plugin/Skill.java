package base.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Jdog653
 */
public class Skill
{
    private SkillType TYPE;
    private int currentXp;

    public Skill(final SkillType t)
    {
        this(t, 0);
    }

    public Skill(final SkillType t, final int currentXp)
    {
        TYPE = t;

        this.currentXp = currentXp;
        if(getCurrentLevel() > 100 || getCurrentLevel() < 1)
        {
            throw new IllegalArgumentException("The level that was entered is wrong. Check your math, please.");
        }
    }

    public void addExp(final int exp, String player)
    {
        byte oldLevel = getCurrentLevel(), currentLevel;
        currentXp += exp;
        currentLevel = getCurrentLevel();

        if(oldLevel < currentLevel)
        {
            for (Player x : Bukkit.getOnlinePlayers())
            {
                if (x.getName().equalsIgnoreCase(player))
                {
                    x.sendMessage(ChatColor.DARK_RED + "Congratulations! You've just advanced your skill in " +
                                          ChatColor.YELLOW + TYPE +
                                          ChatColor.DARK_RED + "! (" +
                                          ChatColor.YELLOW + getCurrentLevel() +
                                          ChatColor.DARK_RED + ")");
                }
            }
            if (currentLevel == 100)
            {
                for (Player y : Bukkit.getOnlinePlayers())
                {
                    if (y != null && y.isOnline())
                    {
                        y.sendMessage(ChatColor.GOLD + player +
                                      ChatColor.RED + " has mastered the art of " +
                                      ChatColor.GOLD + TYPE +
                                      ChatColor.RED + "!");
                    }
                }
            }
        }
        //Uh-Oh
        else if(oldLevel > currentLevel)
        {
            throw new IllegalStateException("After " + exp + " experience, the level decreased from " + oldLevel + " to " + currentLevel + ".");
        }
    }

    public byte getCurrentLevel()
    {
        return TYPE.getLevel(currentXp);
    }

    public int getCurrentXp()
    {
        return currentXp;
    }

    public int getXpToGo()
    {
        return TYPE.getExperienceForLevel((byte) (getCurrentLevel() + (byte) 1)) - getCurrentXp();
    }

    public SkillType getType()
    {
        return TYPE;
    }
}
