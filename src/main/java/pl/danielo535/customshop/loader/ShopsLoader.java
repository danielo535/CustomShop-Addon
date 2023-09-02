package pl.danielo535.customshop.loader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.danielo535.customshop.CustomShop;
import pl.danielo535.customshop.object.Custom;
import pl.danielo535.customshop.object.Product;
import pl.danielo535.customshop.object.Shop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopsLoader {

    private final CustomShop plugin;
    private final HashMap<String, Shop> shops = new HashMap<>();

    public CustomShop getPlugin() {
        return plugin;
    }

    public HashMap<String, Shop> getShops() {
        return shops;
    }
    public ShopsLoader(CustomShop plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    public void loadShops() {
        final File dataFolder = new File(this.plugin.getDataFolder(), "shops");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        final List<FileConfiguration> configs = new ArrayList<>();
        for (File file : dataFolder.listFiles()) {
            configs.add(YamlConfiguration.loadConfiguration(file));
        }
        for (FileConfiguration shopConfigs : configs) {
            final Shop shop = new Shop(shopConfigs.getString("name"), shopConfigs.getString("shop-title"),
                    shopConfigs.getInt("shop-size"), shopConfigs
            );
            for (String string : shopConfigs.getConfigurationSection("products").getKeys(false)) {
                final Product product = new Product(string, shopConfigs.getString("products." + string + ".item-name"),
                        shopConfigs.getInt("products." + string + ".item-slot"),
                        shopConfigs.getString("products." + string + ".item-material"),
                        shopConfigs.getDouble("products." + string + ".cost")
                );
                product.setLore(shopConfigs.getStringList("products." + string + ".item-lore"));
                product.setCommands(shopConfigs.getStringList("products." + string + ".commands"));
                shop.getProducts().put(string, product);
            }
            for (String string : shopConfigs.getConfigurationSection("custom").getKeys(false)) {
                final Custom custom = new Custom(string, shopConfigs.getString("custom." + string + ".item-name"),
                        shopConfigs.getInt("custom." + string + ".item-slot"),
                        shopConfigs.getString("custom." + string + ".item-material")
                );
                custom.setLore(shopConfigs.getStringList("custom." + string + ".item-lore"));
                custom.setCommands(shopConfigs.getStringList("custom." + string + ".commands"));
                shop.getFilters().put(string, custom);
            }
            this.shops.put(shopConfigs.getString("name"), shop);
        }
    }

    public void reload() {
        this.unloadShops();
        this.loadShops();
    }

    private void unloadShops() {
        this.shops.clear();
    }
}