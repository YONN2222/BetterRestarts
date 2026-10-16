<center>

![BetterRestarts](https://raw.githubusercontent.com/YONN2222/BetterRestarts/main/assets/banner.png)

![Java 21+](https://img.shields.io/badge/Java-21+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spigot, Paper, Folia](https://img.shields.io/badge/Spigot%20%7C%20Paper%20%7C%20Folia-2C2C2C?style=flat-square)
![Minecraft 1.21+](https://img.shields.io/badge/Minecraft-1.21+-DFAB40?style=flat-square)
![GPLv3](https://img.shields.io/badge/License-GPLv3-DF6440?style=flat-square)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-2C2C2C?style=flat-square&logo=github)](https://github.com/YONN2222/BetterRestarts)
[![Documentation](https://img.shields.io/badge/Documentation-Docs-DFAB40?style=flat-square)](https://github.com/YONN2222/BetterRestarts/tree/main/docs)
[![Issues](https://img.shields.io/badge/Issues-Report%20a%20bug-DF6440?style=flat-square)](https://github.com/YONN2222/BetterRestarts/issues)

</center>

Type `/restart 10m` and everybody sees an action bar countdown, titles at 10, 5 and 1 minute and a big countdown for the last 10 seconds. Then the server restarts on its own.

One jar runs on Spigot, Paper, Purpur and Folia, Minecraft 1.21 and newer. Open source under GPLv3, no dependencies.

![Action bar countdown](https://raw.githubusercontent.com/YONN2222/BetterRestarts/main/assets/gallery/01-action-bar.png)

## Features

- `/restart <time> [reason]` with free time input: `now`, `30s`, `5m`, `1h30m`, with tab completion
- Action bar countdown for everybody, also for players who join during the countdown
- Titles at 10, 5 and 1 minute, a title countdown for the last 10 seconds, chat reminders and sounds
- Run the command again during a countdown and it is updated, `+5m` and `-1m` change it relatively
- `/restart cancel`, `/restart status` and `/restart reload`
- Automatic restarts at fixed times and weekdays
- Several restart methods: plain shutdown, Spigot restart, a panel API call or any console command
- Replaces the built-in `/restart`, so nothing conflicts
- 10 languages and every message is editable
- Every display, timing and sound can be changed or switched off
- Works with Bedrock players through Geyser

![Title at 10 minutes](https://raw.githubusercontent.com/YONN2222/BetterRestarts/main/assets/gallery/02-title-10-minutes.png)

## Commands

| Command | Permission |
|---|---|
| `/restart <time> [reason]` | `betterrestarts.restart` |
| `/restart +<time>` and `/restart -<time>` | `betterrestarts.restart` |
| `/restart cancel` | `betterrestarts.cancel` |
| `/restart status` | `betterrestarts.status` |
| `/restart reload` | `betterrestarts.reload` |

All permissions default to operators and work with any permissions plugin.

![Chat and commands](https://raw.githubusercontent.com/YONN2222/BetterRestarts/main/assets/gallery/05-chat-and-commands.png)

![Tab completion](https://raw.githubusercontent.com/YONN2222/BetterRestarts/main/assets/gallery/06-tab-completion.png)

![Final countdown](https://raw.githubusercontent.com/YONN2222/BetterRestarts/main/assets/gallery/03-final-countdown.png)

## Configuration

Everything is in `config.yml` with English comments. Changes apply with `/restart reload`.

![config.yml](https://raw.githubusercontent.com/YONN2222/BetterRestarts/main/assets/gallery/09-config.png)

## Languages

English, Chinese, Spanish, Hindi, French, Arabic, Portuguese, Russian, German and Japanese are included. Hindi and Arabic are not rendered well by the default Minecraft font. Copy a file in `plugins/BetterRestarts/lang` to make your own.

![Languages](https://raw.githubusercontent.com/YONN2222/BetterRestarts/main/assets/gallery/08-languages.png)

## Bringing the server back

BetterRestarts can only stop the server. Minecraft cannot start itself again, so your host, panel or start script has to bring it back up. Most hosts do this by default. Details: [Restart methods](https://github.com/YONN2222/BetterRestarts/blob/main/docs/restart-methods.md).

## Links

- [Documentation](https://github.com/YONN2222/BetterRestarts/tree/main/docs)
- [Source code](https://github.com/YONN2222/BetterRestarts)
- [Issues](https://github.com/YONN2222/BetterRestarts/issues)

BetterRestarts sends anonymous usage statistics to bStats. Turn it off for all plugins in `plugins/bStats/config.yml`.
