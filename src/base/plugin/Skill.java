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
        if(getCurrentLevel() > 100 || getCurrentLevel() <= 0)
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

        }
        //Uh-Oh
        else if(oldLevel < currentLevel)
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
