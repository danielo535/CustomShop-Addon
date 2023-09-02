package pl.danielo535.customshop.menu;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pl.danielo535.customwallet.CustomWallet;
import pl.danielo535.customwallet.manager.WalletManager;
import pl.danielo535.customshop.CustomShop;
import pl.danielo535.customshop.object.Product;
import pl.danielo535.customshop.object.Shop;

import java.util.List;

import static pl.danielo535.customshop.utility.ColorUtils.colorText;
import static pl.danielo535.customshop.utility.ColorUtils.colorList;

public class ConfirmationMenu {
    private final CustomShop plugin = CustomShop.getPlugin(CustomShop.class);
    WalletManager walletManager = CustomWallet.getInstance().getWalletManager();
    private final YamlDocument menuConfig = plugin.getConfirmationMenuConfig();
    private final YamlDocument messages = plugin.getMessagesConfig();
    @SuppressWarnings("FieldCanBeLocal")

    private final Product product;
    private final Shop shop;
    private final String user;

    public ConfirmationMenu(Product product, Shop shop, Player player) {
        this.product = product;
        this.shop = shop;
        this.user = player.getName();
        this.open(player);
    }

    @SuppressWarnings("deprecation")
    private void open(Player player) {
        final Gui gui = Gui.gui()
                .title(Component.text(menuConfig.getString("menu-settings.gui-title")))
                .rows(menuConfig.getInt("menu-settings.gui-size"))
                .create();
        gui.getInventory().clear();
        gui.setDefaultClickAction(event -> event.setCancelled(true));
        if (product.getCost() != 0.0) {
            final GuiItem acceptItem = ItemBuilder.from(Material.valueOf(menuConfig.getString("menu-settings.accept-item")))
                    .setName(colorText(menuConfig.getString("menu-settings.accept-name")))
                    .setLore(colorList(menuConfig.getStringList("menu-settings.accept-lore")))
                    .asGuiItem(event -> {
                        if (walletManager.checkWalletMoney(player) < product.getCost()) {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            player.sendMessage(colorText(PlaceholderAPI.setPlaceholders(player, messages.getString("errors.not-enough-balance"))));
                        } else {
                            player.sendMessage(colorText(PlaceholderAPI.setPlaceholders(player, messages.getString("successfully-purchased")
                                    .replace("{PRODUCT}", product.getName()))
                            ));
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 8);
                            walletManager.removeWalletMoney(player, product.getCost());
                            runCommands(product.getCommands(), player);
                        }
                    });
            gui.setItem(menuConfig.getInt("menu-settings.accept-item-slot"), acceptItem);
            final GuiItem cancelItem = ItemBuilder.from(Material.valueOf(menuConfig.getString("menu-settings.cancel-item")))
                    .setName(colorText(menuConfig.getString("menu-settings.cancel-name")))
                    .setLore(colorList(menuConfig.getStringList("menu-settings.cancel-lore")))
                    .asGuiItem(event -> {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        new ShopMenu(shop, player);
                    });
            gui.setItem(menuConfig.getInt("menu-settings.cancel-item-slot"), cancelItem);
            gui.open(player);
        } else if (product.getCommands() != null){
            runCommands(product.getCommands(), player);
        }
    }

    private void runCommands(List<String> commands, Player player) {
        plugin.getServer().getScheduler().runTask(plugin, () -> commands.forEach(command -> Bukkit.dispatchCommand(
                plugin.getServer().getConsoleSender(),
                command.replace("{PLAYER}", player.getName())
        )));
    }
}
