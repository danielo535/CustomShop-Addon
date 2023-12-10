package pl.danielo535.customshop.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import pl.danielo535.customshop.object.Custom;
import pl.danielo535.customshop.object.Product;
import pl.danielo535.customshop.object.Shop;

import java.util.UUID;

import static pl.danielo535.customshop.utility.ColorUtils.colorText;
import static pl.danielo535.customshop.utility.ColorUtils.colorList;

public class ShopMenu {
    private final Shop shop;
    ConfirmationMenu confirmationMenu = ConfirmationMenu.getInstance();

    public ShopMenu(Shop shop, Player player) {
        this.shop = shop;
        this.open(player);
    }

    private void open(Player player) {
        final Gui gui = Gui.gui()
                .title(Component.text(colorText(shop.getTitle())))
                .rows(shop.getSize())
                .create();
        gui.getInventory().clear();
        gui.setDefaultClickAction(event -> event.setCancelled(true));
        for (Product product : shop.getProducts().values()) {
            final GuiItem guiItem;
            guiItem = ItemBuilder.from(product.getMaterial()).setName(colorText(PlaceholderAPI.setPlaceholders(player, product.getName())))
                    .setLore(colorList(PlaceholderAPI.setPlaceholders(player,product.getLore())))
                    .asGuiItem(event -> confirmationMenu.setParameters(product, shop, player));
            gui.setItem(product.getSlot(), guiItem);
        }
        for (Custom custom : shop.getFilters().values()) {
            final GuiItem guiItem;
            guiItem = ItemBuilder.from(custom.getMaterial()).setName(colorText(PlaceholderAPI.setPlaceholders(player, custom.getName())))
                    .setLore(colorList(PlaceholderAPI.setPlaceholders(player, custom.getLore())))
                    .asGuiItem(event -> confirmationMenu.setParameters(custom, shop, player));
            gui.setItem(custom.getSlot(), guiItem);
        }
        gui.open(player);
    }
}
