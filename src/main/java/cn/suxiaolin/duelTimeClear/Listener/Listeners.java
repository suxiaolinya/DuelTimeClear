package cn.suxiaolin.duelTimeClear.Listener;

import cn.suxiaolin.duelTimeClear.DuelTimeClear;
import cn.valorin.dueltime.event.arena.ArenaEndEvent;
import cn.valorin.dueltime.event.arena.ArenaTryToStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Listeners implements Listener {

    private Map<String, ArrayList<Location>> blockMap = new HashMap<>();
    private Map<String, ArrayList<String>> playername = new HashMap<>();
    private DuelTimeClear plugin;

    public Listeners(DuelTimeClear plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onArenaStart(ArenaTryToStartEvent event) {
        String arena = event.getArena().getName();
        Player[] players = event.getPlayers();
        ArrayList<String> playerlist = new ArrayList<>();
        for (Player player : players) {
            playerlist.add(player.getName());
        }
        playername.put(arena, playerlist);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            for (String key : playername.keySet()) {
                ArrayList<String> playerlist = playername.get(key);
                if (playerlist.contains(player.getName())) {
                    if (blockMap.get(key) != null) {
                        ArrayList<Location> var1 = blockMap.get(key);
                        var1.add(event.getBlock().getLocation());
                        blockMap.replace(key, var1);
                    } else {
                        ArrayList<Location> var1 = new ArrayList<>();
                        var1.add(event.getBlock().getLocation());
                        blockMap.put(key, var1);
                    }

                }

            }
        });
    }
    @EventHandler
    public void onArenaFinished(ArenaEndEvent event){
        String arena = event.getArena().getName();
            for (String key : blockMap.keySet()) {
                if (key.equals(arena)) {
                    for (Location location : blockMap.get(key)) {
                        location.getBlock().setType(Material.AIR);
                    }
                    blockMap.remove(key);
                }
            }
            playername.remove(arena);
    }

}
