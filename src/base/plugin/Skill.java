package base.plugin;

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

    public void addExp(final int exp)
    {
        byte oldLevel = getCurrentLevel(), currentLevel;
        currentXp += exp;
        currentLevel = getCurrentLevel();

        if(oldLevel < currentLevel)
        {
            /*
             * TODO: notify player
             * To do that, we need to get the parent player of this skill
             * as well as the skill's name in lowercase letters. There
             * is now a static StatHandler available so that we can
             * access the HashMap that contains the player.
             *
             * We do not need to directly increase skill effectiveness,
             * because the skill's effectiveness can be checked on a
             * per-event basis using the skill's current level.
             */
            if (currentLevel == 100)
            {
                // TODO: notify all players if someone masters a skill
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

    public SkillType getType()
    {
        return TYPE;
    }
}
