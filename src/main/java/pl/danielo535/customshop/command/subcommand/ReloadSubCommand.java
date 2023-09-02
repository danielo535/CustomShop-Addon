package pl.danielo535.customshop.command.subcommand;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.command.CommandSender;
import pl.danielo535.customshop.command.ShopCommand;
import pl.danielo535.customshop.loader.ShopsLoader;

import java.io.IOException;
import java.util.*;

import static pl.danielo535.customshop.utility.ColorUtils.colorText;

public class ReloadSubCommand extends ShopCommand {
    private final ShopsLoader shopsLoader;
    private final YamlDocument messages;

    public ReloadSubCommand(ShopsLoader shopsLoader, YamlDocument messages) {
        super("customshop", "", Arrays.asList("ashop"));
        this.shopsLoader = shopsLoader;
        this.messages = messages;
    }

    @Override
    public void exec(CommandSender sender, String[] args) throws IOException {
        if (args.length == 0) {
            sender.sendMessage(colorText(messages.getString("errors.incorrect-usage")));
        } else {
            if (sender.hasPermission("CustomShop.reload")) {
                shopsLoader.reload();
                messages.reload();
                sender.sendMessage(colorText(messages.getString("reloaded")));
            } else {
                sender.sendMessage(colorText(messages.getString("errors.no-permission")));
            }
        }
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return Collections.emptyList();
        }
        final List<String> matches = new ArrayList<>();
        matches.add("reload");

        return matches;
    }
}
