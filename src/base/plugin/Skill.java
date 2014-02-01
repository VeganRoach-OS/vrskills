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

    public boolean addExp(final int exp)
    {
        byte oldLevel = getCurrentLevel(), currentLevel;
        currentXp += exp;
        currentLevel = getCurrentLevel();

        if(oldLevel < currentLevel)
        {
            if (currentLevel == 100)
            {
                // TODO: get player name
                for (Player x : Bukkit.getOnlinePlayers())
                {
                    if (x != null && x.isOnline())
                    {
                        x.sendMessage(ChatColor.GOLD + "Someone" +
                                      ChatColor.RED + " has mastered the art of " +
                                      ChatColor.GOLD + TYPE +
                                      ChatColor.RED + "!");
                    }
                }
            }
            return true;
        }
        //Uh-Oh
        else if(oldLevel > currentLevel)
        {
            throw new IllegalStateException("After " + exp + " experience, the level decreased from " + oldLevel + " to " + currentLevel + ".");
        }

        return false;
    }

    public byte getCurrentLevel()
    {
        return TYPE.getLevel(currentXp);
    }

    public int getCurrentXp()
    {
        return currentXp;
    }

    public SkillType getType()
    {
        return TYPE;
    }
}
