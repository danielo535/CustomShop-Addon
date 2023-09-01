package pl.danielo535.customshop.listener;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.danielo535.customshop.CustomShop;
import pl.danielo535.customshop.update.CheckUpdate;

import static pl.danielo535.customshop.utility.ColorUtils.colorText;

public class JoinServerListener implements Listener {
    private final CustomShop plugin;

    public JoinServerListener(CustomShop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            new CheckUpdate(plugin, 112422).getVersion(version -> {
                if (!(plugin.getDescription().getVersion().equals(version))) {
                    player.sendMessage(colorText("&7[&6CustomWallet-Shop&7] &aThere is a new update available."));
                    TextComponent message = new TextComponent(colorText("&7[&6CustomWallet-Shop&7] Your version &c" + plugin.getDescription().getVersion() + "&7 new version &c" + version));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "https://www.spigotmc.org/resources/112339"));
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Copy download link").create()));
                    player.spigot().sendMessage(message);
                }
            });
        }
    }
}
