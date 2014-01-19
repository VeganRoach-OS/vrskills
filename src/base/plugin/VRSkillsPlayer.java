package base.plugin;

import org.bukkit.entity.Player;

/**
 * @author Jdog653
 */
public class VRSkillsPlayer
{
    private final Player PLAYER;
    private Skill[] skills;

    public VRSkillsPlayer(final Player p)
    {
        this(p, new int[] {0,0,0,0,0,0,0,0,0,0});
    }

    public VRSkillsPlayer(final Player p, final int[] currentXps)
    {
        PLAYER = p;
        skills = new Skill[SkillType.values().length];

        if(currentXps.length != skills.length)
        {
            throw new IllegalArgumentException("The number of given xp is not equal to the current number of skills");
        }

        for(int i = 0; i < skills.length; i++)
        {
            skills[i] = new Skill(SkillType.values()[i], currentXps[i]);
        }
    }
}
