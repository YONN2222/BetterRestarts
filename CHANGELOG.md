# Changelog

## [1.0.1]

- Fixed the plugin failing to start on Spigot (it only worked on Paper and its forks)
- Warn about an unknown restart method or an invalid max-time in config.yml
- Panel URL, server ID and API key are trimmed, so pasted spaces no longer break the panel method

## [1.0.0]

- Schedule restarts with `/restart <time> [reason]`, for example `now`, `30s`, `5m`, `1h30m`
- Tab completion for times, including `+5m` and `-1m` to change a running countdown
- Action bar countdown for everybody, including players who join later
- Titles at 10, 5 and 1 minute, a title countdown for the last 10 seconds, chat reminders and sounds
- `/restart cancel`, `/restart status` and `/restart reload`
- Restarting while a countdown runs updates it and keeps the reason
- Automatic restarts at fixed times and weekdays
- Restart methods: Spigot restart, shutdown, Pterodactyl and Pelican panel API, console command
- Replaces the built-in `/restart` command
- One jar for Spigot, Paper, Purpur and Folia
- Messages in 10 languages: en, zh, es, hi, fr, ar, pt, ru, de, ja
- Permissions: `betterrestarts.restart`, `.cancel`, `.status`, `.reload`
- Optional bStats metrics, disabled globally in the bStats config
