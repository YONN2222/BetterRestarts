<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/schedule-dark.svg">
    <img src="../assets/icons/schedule-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Automatic restarts</h1>

<p align="center">Restart at fixed times, with the same countdown as a manual restart.</p>

<p align="center"><a href="README.md">All docs</a></p>

## Setup

```yaml
schedule:
  enabled: true
  timezone: Europe/Berlin
  times: ["04:00"]
  days: []
  announce-before: 10m
  reason: Scheduled restart
```

| Option | Effect |
|---|---|
| `enabled` | Turns automatic restarts on |
| `timezone` | An IANA timezone like `Europe/Berlin`, or `system` for the timezone of the machine |
| `times` | One or more times in 24 hour format `HH:mm` |
| `days` | Weekdays like `MONDAY`, `FRIDAY`. An empty list means every day |
| `announce-before` | How long before the restart the countdown begins |
| `reason` | The reason shown to players |

Apply changes with `/restart reload`.

## How it behaves

- The countdown starts `announce-before` ahead of the time, and looks exactly like a manual one.
- `/restart status` shows the next automatic restart.
- Cancelling an automatic countdown skips only that one restart. The next day still runs.
- While a countdown is running, no second one starts. A manual `/restart` replaces an automatic countdown.
- A server that starts inside the announcement window never restarts for that time. This prevents restart loops right after a restart.

## Examples

Every night at 4:00 with a 10 minute warning:

```yaml
schedule:
  enabled: true
  times: ["04:00"]
```

Two restarts a day, only on weekends:

```yaml
schedule:
  enabled: true
  times: ["04:00", "16:00"]
  days: [SATURDAY, SUNDAY]
```
