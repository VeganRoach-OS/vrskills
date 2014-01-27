package base.listeners;

import base.plugin.SkillType;
import base.plugin.StatHandler;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

/**
 * Created by Jordan on 1/20/14.
 */
public class PlayerBlockBreakListener implements Listener
{
    private StatHandler plugin;

    public PlayerBlockBreakListener(StatHandler h)
    {
        plugin = h;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        Player player = e.getPlayer();
        int[] expAmounts = new int[SkillType.values().length];

        for (int i = 0; i < SkillType.values().length; i++)
        {
            expAmounts[i] = plugin.experience.getInt(SkillType.values()[i] + ".blocks." + e.getBlock().getType());
        }

        for (int i = 0; i < SkillType.values().length; i++)
        {
            if (plugin.playerSkills.get(player.getName())[i].addExp(expAmounts[i]))
                player.sendMessage("Level up!");
        }
    }
}
