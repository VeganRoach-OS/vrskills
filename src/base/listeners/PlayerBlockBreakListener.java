package base.listeners;

import base.plugin.StatHandler;
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
    }
}
