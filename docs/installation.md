<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/install-dark.svg">
    <img src="../assets/icons/install-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Installation</h1>

<p align="center">Get BetterRestarts running in a minute.</p>

<p align="center"><a href="README.md">All docs</a></p>

## Requirements

- Any Bukkit based server on Minecraft 1.21 or newer: Spigot, Paper, Purpur, Folia and forks of them
- Java 21 or newer

The same jar runs on all of them. The plugin only uses the Bukkit API and picks the Folia schedulers by itself when they exist. Proxies like Velocity or BungeeCord and mod loaders like Forge or Fabric are not supported.

## Steps

1. Download the jar from [Modrinth](https://modrinth.com/plugin/better-restarts), [Hangar](https://hangar.papermc.io/YONN2222/BetterRestarts) or the [GitHub releases](https://github.com/YONN2222/BetterRestarts/releases).
2. Put it into the `plugins` folder.
3. Start the server. The plugin creates `plugins/BetterRestarts/config.yml` and a `lang` folder.
4. Give your staff the permissions, operators already have them. See [Commands](commands.md#permissions).
5. Decide how the server comes back after a restart, see [Restart methods](restart-methods.md). The default `shutdown` works with Pterodactyl and Pelican without further setup, see [Pterodactyl and Pelican](pterodactyl-pelican.md).

## First test

Run this on the console or in game:

```
/restart 30s test
```

You should see the action bar, the chat message and the final countdown. Cancel with `/restart cancel` if you do not want the server to restart.

## Updating

Replace the jar and restart the server. New options are not added to an existing `config.yml` automatically. Compare it with the [configuration reference](configuration.md) after an update.
