package pl.danielo535.customshop.object;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private final String product;
    private final String name;
    private final int slot;
    private List<String> lore = new ArrayList<>();

    private final Material material;

    private final double cost;

    private List<String> commands = new ArrayList<>();

    public String getProduct() {
        return product;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getSlot() {
        return slot;
    }
    public Material getMaterial() {
        return material;
    }

    public double getCost() {
        return cost;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public Product(String product, String name, int slot, String material, double cost) {
        this.product = product;
        this.name = name;
        this.slot = slot;
        this.material = Material.valueOf(material);
        this.cost = cost;
    }
}