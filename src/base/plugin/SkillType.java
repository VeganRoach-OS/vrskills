package base.plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jdog653
 */
public enum SkillType
{
    ARCHERY(ExperienceGroup.FLUCTUATING),
    AXES(ExperienceGroup.FLUCTUATING),
    EXCAVATION(ExperienceGroup.MEDIUM_SLOW),
    FISHING(ExperienceGroup.FAST),
    HERBALISM(ExperienceGroup.MEDIUM_FAST),
    MINING(ExperienceGroup.MEDIUM_SLOW),
    SWORDPLAY(ExperienceGroup.FLUCTUATING),
    TAMING(ExperienceGroup.ERRATIC),
    UNARMED_COMBAT(ExperienceGroup.FLUCTUATING),
    WOODCUTTING(ExperienceGroup.MEDIUM_SLOW);

    final ExperienceGroup GROUP;
    SkillType(final ExperienceGroup g)
    {
        GROUP = g;
    }

    public int getExperienceForLevel(final byte level)
    {
        return GROUP.getExpForLevel(level);
    }

    public byte getLevel(final int exp)
    {
        return GROUP.getLevel(exp);
    }

    private enum ExperienceGroup
    {
        ERRATIC,
        FAST,
        MEDIUM_FAST,
        MEDIUM_SLOW,
        SLOW,
        FLUCTUATING;

        private Map<Integer, Byte> levelMap;

        ExperienceGroup()
        {
            levelMap = new HashMap<>();

            for(byte i = 1; i <= 100; i++)
            {
                levelMap.put(getExpForLevel(i), i);
            }
        }

        public byte getLevel(final int exp)
        {
            return levelMap.get(exp);
        }

        public int getExpForLevel(final byte level)
        {
            switch(this)
            {
                case ERRATIC:
                    return calcErratic(level);
                case FAST:
                    return calcFast(level);
                case MEDIUM_FAST:
                    return calcMedFast(level);
                case MEDIUM_SLOW:
                    return calcMedSlow(level);
                case SLOW:
                    return calcSlow(level);
                case FLUCTUATING:
                    return calcFluctuating(level);
                default:
                    throw new IllegalStateException("Enum " + this.toString().toLowerCase() + " is illlegal");
            }
        }

        private int calcErratic(final byte level)
        {
            if(level > 0 && level <= 50)
            {
                return (int)((Math.pow(level, 3) * (100 - level)) / 50.0);
            }
            else if(level > 50 && level <= 68)
            {
                return (int)((Math.pow(level, 3) * (150 - level)) / 100.0);
            }
            else if(level > 68 && level <= 98)
            {
                return (int)((Math.pow(level, 3) * ((1911 - 10 * level) / 3.0)) / 500.0);
            }
            else if(level > 98 && level <= 100)
            {
                return (int)((Math.pow(level, 3) * (160 - level)) / 100.0);
            }
            else
            {
                throw new IllegalArgumentException("A level of " + level + " is not allowed");
            }
        }

        private int calcFast(final byte level)
        {
            if(level > 0 && level <= 100)
            {
                return (int)((4 * Math.pow(level, 3)) / 5.0);
            }

            throw new IllegalArgumentException("Level " + level + " is not allowed");
        }

        private int calcMedFast(final byte level)
        {
            if(level > 0 && level <= 100)
            {
                return (int)Math.pow(level, 3);
            }

            throw new IllegalArgumentException("Level " + level + " is not allowed");
        }

        private int calcMedSlow(final byte level)
        {
            if(level > 0 && level <= 100)
            {
                return (int)(((6 * Math.pow(level, 3)) / 5.0) - (15 * Math.pow(level, 2)) + (100 * level) - 140);
            }

            throw new IllegalArgumentException("Level " + level + " is not allowed");
        }

        private int calcSlow(final byte level)
        {
            if(level > 0 && level <= 100)
            {
                return (int)((5 * Math.pow(level, 3)) / 4.0);
            }

            throw new IllegalArgumentException("Level " + level + " is not allowed");
        }

        private int calcFluctuating(final byte level)
        {
            if(level > 0 && level <= 15)
            {
                return (int)(Math.pow(level, 3) * ((((level + 1) / 3.0) + 24) / 50.0));
            }
            else if(level > 15 && level <= 36)
            {
                return (int)(Math.pow(level, 3) * ((level + 14) / 50.0));
            }
            else if(level > 36 && level <= 100)
            {
                return (int)(Math.pow(level, 3) * (((level / 2.0) + 32) / 50.0));
            }
            else
            {
                throw new IllegalArgumentException("A level of " + level + " is not allowed");
            }
        }
    }
}
