package base.plugin;

/**
 * @author Jdog653
 */
public class Skill
{
    private SkillType TYPE;
    private int currentXp, maxXp, currentLevel;

    public Skill(final SkillType t)
    {
        this(t, 0);
    }

    public Skill(final SkillType t, final int currentXp)
    {
        TYPE = t;
        this.currentXp = currentXp;
        currentLevel = calculateLevel(currentXp);
    }

    public SkillType getType()
    {
        return TYPE;
    }

    private int calculateLevel(final int xp)
    {
        //TODO Figure out a way to calculate levels based on XP
        return 0;
    }
}
