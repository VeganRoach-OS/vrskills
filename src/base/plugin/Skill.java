package base.plugin;

import org.bukkit.entity.Player;

/**
 * @author 2mac
 */
public enum Skill
{
    ARCHERY,
    AXES,
    EXCAVATION,
    FISHING,
    HERBALISM,
    MINING,
    SWORDPLAY,
    TAMING,
    UNARMED_COMBAT,
    WOODCUTTING;

    // todo: calculate level constant
    public static final float LEVEL_CONST = 1f;

    private byte maxLevel, currentLevel;
    private int nextXp, currentXp;

    Skill()
    {
        maxLevel = 100;
    }

    public void gainXp(Player p, int amount)
    {
        currentXp += amount;
        if (currentXp >= nextXp)
        {
            levelUp(p);
        }
    }

    public void levelUp(Player p)
    {
        if (currentLevel < maxLevel)
        {
            // todo: notify player
            currentXp -= nextXp;
            currentLevel++;
            nextXp *= LEVEL_CONST;
            gainXp(p,0);
        }
    }
}
