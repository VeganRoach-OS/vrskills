package base.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author 2mac
 */
public class StatHandler extends JavaPlugin
{
    public void onEnable()
    {}

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        return false;
    }

    public void onDisable()
    {}
}
