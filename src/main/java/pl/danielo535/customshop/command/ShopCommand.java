package pl.danielo535.customshop.command;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import pl.danielo535.customshop.CustomShop;

import java.util.List;

public abstract class ShopCommand extends Command implements PluginIdentifiableCommand {
    protected ShopCommand(String name, String usage, List<String> aliases) {
        super(name, "CustomWallet plugin command", usage, aliases);
    }

    @NotNull
    @Override
    public Plugin getPlugin() {
        return CustomShop.getProvidingPlugin(CustomShop.class);
    }

    public abstract void exec(CommandSender sender, String[] args);

    public abstract List<String> complete(CommandSender sender, String[] args);

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        exec(sender, args);
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return complete(sender, args);
    }
}