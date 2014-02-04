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
        if (getCurrentLevel() >= 100) return;
        byte oldLevel = getCurrentLevel(), currentLevel;
        try
        {
            currentXp += exp;
            currentLevel = getCurrentLevel();
        }catch (IllegalStateException e)
        {
            // experience to be added goes over maximum
            setLevel((byte)100);
            currentLevel = getCurrentLevel();
        }

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

    private void setXp(int amount)
    {
        currentXp = amount;
    }

    public void setLevel(byte targetLevel)
    {
        if (targetLevel > 100)
            throw new IllegalArgumentException("Cannot set level higher than maximum.");
        setXp(TYPE.getExperienceForLevel(targetLevel));
    }

    public byte getCurrentLevel()
    {
        return TYPE.getLevel(currentXp);
    }

    public int getCurrentXp()
    {
        return currentXp;
    }

    public int getXpToGo(byte level)
    {
        return TYPE.getExperienceForLevel((byte) (level + (byte) 1)) - getCurrentXp();
    }

    public SkillType getType()
    {
        return TYPE;
    }
}
