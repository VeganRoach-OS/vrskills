package base.plugin;

/**
 * Created by Jordan on 1/23/14.
 */
public enum ConfigFile
{
    EXPERIENCE("experience.yml");

    private final String FILE;

    ConfigFile(final String s)
    {
        FILE = s;
    }

    public String getFile()
    {
        return FILE;
    }
}
