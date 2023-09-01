package pl.danielo535.customshop.manager;

import lombok.SneakyThrows;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import pl.danielo535.customshop.CustomShop;
import pl.danielo535.customshop.command.ShopCommand;

import java.lang.reflect.Field;

public class CommandManager {
    private final CustomShop plugin;
    private final SimpleCommandMap commandMap;
    @SuppressWarnings("FieldCanBeLocal")
    private final SimplePluginManager pluginManager;

    @SneakyThrows
    public CommandManager(CustomShop plugin) {
        this.plugin = plugin;
        this.pluginManager = (SimplePluginManager) plugin.getServer().getPluginManager();
        Field field = SimplePluginManager.class.getDeclaredField("commandMap");
        field.setAccessible(true);
        this.commandMap = (SimpleCommandMap) field.get(pluginManager);
    }

    public void registerCommand(ShopCommand command) {
        this.commandMap.register(this.plugin.getDescription().getName(), command);
    }
}
