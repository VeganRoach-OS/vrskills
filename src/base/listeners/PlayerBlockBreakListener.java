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
        int[] expAmounts = new int[StatHandler.SKILL_TYPE_COUNT];

        for (int i = 0; i < StatHandler.SKILL_TYPE_COUNT; i++)
        {
            expAmounts[i] = plugin.experience.getInt(SkillType.values()[i] + ".blocks." + e.getBlock().getType().toString());
            player.sendMessage(SkillType.values()[i] + ".blocks." + e.getBlock().getType().toString());
        }

        for (int i = 0; i < StatHandler.SKILL_TYPE_COUNT; i++)
        {
            plugin.playerSkills.get(player.getName())[i].addExp(expAmounts[i]);
            player.sendMessage("Added " + expAmounts[i] + " to " + SkillType.values()[i]);
        }
    }
}
