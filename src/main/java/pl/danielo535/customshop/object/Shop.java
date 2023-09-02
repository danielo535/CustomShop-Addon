package pl.danielo535.customshop.object;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private final String name;
    private final String title;
    private final int size;
    private final FileConfiguration config;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public FileConfiguration getConfig() {
        return config;
    }
    private final Map<String, Product> products = new HashMap<>();
    private final Map<String, Custom> filters = new HashMap<>();
    public Map<String, Product> getProducts() {
        return products;
    }
    public Map<String, Custom> getFilters() {
        return filters;
    }
    public Shop(String name, String title, int size, FileConfiguration config) {
        this.name = name;
        this.title = title;
        this.size = size;
        this.config = config;
    }

}