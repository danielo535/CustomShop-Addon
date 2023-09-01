package pl.danielo535.customshop.menu;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import pl.danielo535.customshop.object.Product;
import pl.danielo535.customshop.object.Shop;

import static pl.danielo535.customshop.utility.ColorUtils.colorText;
import static pl.danielo535.customshop.utility.ColorUtils.colorList;

public class ShopMenu {
    private final Shop shop;

    public ShopMenu(Shop shop, Player player) {
        this.shop = shop;
        this.open(player);
    }

    @SuppressWarnings("deprecation")
    private void open(Player player) {
        final Gui gui = Gui.gui()
                .title(Component.text(shop.getTitle()))
                .rows(shop.getSize())
                .create();
        gui.getInventory().clear();
        gui.setDefaultClickAction(event -> event.setCancelled(true));
        for (Product product : shop.getProducts().values()) {
            final GuiItem guiItem;
            guiItem = ItemBuilder.from(product.getMaterial()).setName(colorText(product.getName()))
                    .setLore(colorList(product.getLore()))
                    .asGuiItem(event -> new ConfirmationMenu(product, shop, player));
            gui.setItem(product.getSlot(), guiItem);
        }
        gui.open(player);
    }
}
