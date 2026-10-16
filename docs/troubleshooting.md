<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/help-dark.svg">
    <img src="../assets/icons/help-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Troubleshooting</h1>

<p align="center">Common questions and fixes.</p>

<p align="center"><a href="README.md">All docs</a></p>

## The server stops but does not come back

Something has to start it again. See [Restart methods](restart-methods.md). With Pterodactyl or Pelican check crash detection or use the [panel method](pterodactyl-pelican.md).

## /restart still runs the old command

The built-in command is only replaced when `command.replace-built-in` is `true`. A change of this option needs a server restart. Another plugin that registers `restart` can also take the name, check `/help restart` and change `command.name` if needed.

## "You do not have permission"

Give the player the permission from [Commands](commands.md#permissions), for example `betterrestarts.restart`.

## Players are not kicked

`restart.kick-players` must be `true`. Players are kicked `delay-ticks` after the "restarting" title.

## A countdown started by itself

Automatic restarts are enabled, see [Automatic restarts](schedule.md). Turn them off with `schedule.enabled: false` and `/restart reload`. Cancel the running one with `/restart cancel`.

## The countdown looks wrong after a lag spike

The remaining time is based on the system clock, not on server ticks, so lag does not make it slower. Reminders and titles that were skipped during a spike are caught up.

## Special characters show as ?

The server console may not use UTF-8. Start Java with `-Dfile.encoding=UTF-8`. In game the characters are shown correctly.

## Bedrock players

Titles and the action bar are sent through the normal Bukkit API and should show on Geyser. If something does not show, open an issue.

## Nothing about my problem here

Open an issue at [github.com/YONN2222/BetterRestarts/issues](https://github.com/YONN2222/BetterRestarts/issues) with your server version, the plugin version and the log.
