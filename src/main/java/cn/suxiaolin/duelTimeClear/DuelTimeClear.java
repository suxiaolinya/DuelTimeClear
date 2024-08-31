package cn.suxiaolin.duelTimeClear;

import cn.suxiaolin.duelTimeClear.Listener.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DuelTimeClear extends JavaPlugin {

    @Override
    public void onEnable() {

        if (Bukkit.getPluginManager().isPluginEnabled("DuelTime")) {
            Bukkit.getConsoleSender().sendMessage("[DuelTimeClear]§2前置DuelTime已找到,本插件已启用!");
        }else{
            Bukkit.getPluginManager().disablePlugin(this);
        }

        new Listeners(this);
        new Metrics(this, 23244);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[DuelTimeClear]§4插件已卸载!");
    }
}
