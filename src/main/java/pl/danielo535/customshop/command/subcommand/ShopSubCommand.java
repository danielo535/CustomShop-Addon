package pl.danielo535.customshop.command.subcommand;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.danielo535.customshop.command.ShopCommand;
import pl.danielo535.customshop.loader.ShopsLoader;
import pl.danielo535.customshop.menu.ShopMenu;

import java.util.*;

import static pl.danielo535.customshop.utility.ColorUtils.colorText;

public class ShopSubCommand extends ShopCommand {
    private final ShopsLoader shopsLoader;
    private final YamlDocument messages;

    public ShopSubCommand(ShopsLoader shopsLoader, YamlDocument messages) {
        super("shop", "", Arrays.asList("shops", "store"));
        this.shopsLoader = shopsLoader;
        this.messages = messages;
    }

    @Override
    public void exec(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(colorText(messages.getString("errors.incorrect-usage")));
            } else {
                if (this.shopsLoader.getShops().get(args[0]) != null) {
                    new ShopMenu(this.shopsLoader.getShops().get(args[0]), player);
                } else {
                    player.sendMessage(colorText(messages.getString("errors.shop-not-exists")));
                }
            }
        } else {
            sender.sendMessage(colorText(messages.getString("errors.player-only")));
        }
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return Collections.emptyList();
        }
        final List<String> matches = new ArrayList<>();
        final String search = args[0].toLowerCase(Locale.ROOT);
        for (String shop : this.shopsLoader.getShops().keySet()) {
            if (shop.toLowerCase(Locale.ROOT).startsWith(search)) {
                matches.add(shop);
            }
        }
        return matches;
    }
}
