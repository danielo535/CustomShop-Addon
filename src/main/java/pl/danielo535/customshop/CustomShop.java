package pl.danielo535.customshop;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.danielo535.customshop.command.subcommand.ReloadSubCommand;
import pl.danielo535.customshop.command.subcommand.ShopSubCommand;
import pl.danielo535.customshop.listener.JoinServerListener;
import pl.danielo535.customshop.loader.ShopsLoader;
import pl.danielo535.customshop.manager.CommandManager;
import pl.danielo535.customshop.update.CheckUpdate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class CustomShop extends JavaPlugin {

    @Getter
    private YamlDocument confirmationMenuConfig;
    @Getter
    private YamlDocument messagesConfig;
    @Getter
    private ShopsLoader shopsLoader;

    @SuppressWarnings("ConstantConditions")
    @SneakyThrows
    @Override
    public void onEnable() {
        new Metrics(this,19700);
        if (Bukkit.getPluginManager().getPlugin("CustomWallet") == null) {
            getServer().getPluginManager().disablePlugin(this);
            getLogger().warning("✘ Plugin Disabled: CustomWallet Missing");
            getLogger().info("╭────────────────────────────────────────╮");
            getLogger().info("│ CustomShop has been disabled.          │");
            getLogger().info("│ Please install CustomWallet to enable. │");
            getLogger().info("╰────────────────────────────────────────╯");
        }
        messagesConfig = YamlDocument.create(new File(getDataFolder(), "messages.yml"), getResource("messages.yml"),
                GeneralSettings.builder().setUseDefaults(false).build(),
                LoaderSettings.builder().setAutoUpdate(true).build(),
                DumperSettings.DEFAULT,
                UpdaterSettings.builder().build()
        );
        confirmationMenuConfig = YamlDocument.create(new File(getDataFolder(), "confirmation-menu.yml"), getResource("confirmation-menu.yml"));
        shopsLoader = new ShopsLoader(this);
        final CommandManager commandManager = new CommandManager(this);
        getServer().getPluginManager().registerEvents(new JoinServerListener(this), this);
        commandManager.registerCommand(new ShopSubCommand(shopsLoader,messagesConfig));
        commandManager.registerCommand(new ReloadSubCommand(shopsLoader,messagesConfig));
        exampleShop();
        shopsLoader.loadShops();
        getLogger().info("╭───────────────────────────────────────╮");
        getLogger().info("│ CustomShop Plugin Started             │");
        getLogger().info("│ Version: " + getDescription().getVersion() + "                          │");
        getLogger().info("│ Developer: danielo535                 │");
        getLogger().info("│                                       │");
        getLogger().info("│ Thank you for using CustomShop        │");
        getLogger().info("╰───────────────────────────────────────╯");
        updateCheck();
    }

    private void updateCheck() {
        new CheckUpdate(this, 112422).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                this.getLogger().info("There is not a new update available.");
            } else {
                this.getLogger().info("There is a new update available.");
                this.getLogger().info("Your version " + this.getDescription().getVersion() + " new version " + version);
            }
        });
    }

    @SuppressWarnings({"DataFlowIssue", "ResultOfMethodCallIgnored"})
    private void exampleShop() {
        final File file = new File(getDataFolder(), "shops");
        if (!file.exists()) file.mkdirs();
        if (!file.isDirectory()) throw new UnsupportedOperationException("Shops folder is not directory");
        final File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            try (InputStream inputStream = getClass().getResourceAsStream("/shops/example-shop.yml")) {
                final File exampleShop = new File(file, "example-shop.yml");
                Files.copy(inputStream, exampleShop.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        // empty ;)
    }
}
