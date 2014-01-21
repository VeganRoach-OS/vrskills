package base.listeners;

import base.plugin.Skill;
import base.plugin.SkillType;
import base.plugin.StatHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Jordan on 1/20/14.
 */
public class PlayerLoginListener implements Listener
{
    private StatHandler plugin;

    PlayerLoginListener(StatHandler h)
    {
        plugin = h;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e)
    {
        if(!plugin.playerSkills.containsKey(e.getPlayer().getDisplayName()))
        {
            Skill[] s = new Skill[10];

            for(int i = 0; i < 10; i++)
            {
                s[i] = new Skill(SkillType.values()[i]);
            }

            plugin.playerSkills.put(e.getPlayer().getDisplayName(), s);
        }
    }
}
