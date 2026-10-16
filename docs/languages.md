<p align="center">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="../assets/icons/languages-dark.svg">
    <img src="../assets/icons/languages-light.svg" alt="" width="96">
  </picture>
</p>

<h1 align="center">Languages</h1>

<p align="center">Ten languages included, every text editable.</p>

<p align="center"><a href="README.md">All docs</a></p>

![Titles in eight languages](../assets/gallery/08-languages.png)

The screenshot shows eight of the ten languages. The default Minecraft font cannot display Hindi and Arabic properly, use a resource pack with a suitable font if you need them.

## Choosing a language

Set `language` in `config.yml` and run `/restart reload`.

| Code | Language |
|---|---|
| `en` | English |
| `zh` | Chinese |
| `es` | Spanish |
| `hi` | Hindi |
| `fr` | French |
| `ar` | Arabic |
| `pt` | Portuguese |
| `ru` | Russian |
| `de` | German |
| `ja` | Japanese |

The files are copied to `plugins/BetterRestarts/lang/` on the first start. English and German were checked by the author, the other translations are good faith work. Corrections are welcome as pull requests.

## Changing or adding a language

1. Copy a file in `plugins/BetterRestarts/lang/`, for example `en.yml` to `custom.yml`.
2. Edit the texts.
3. Set `language: custom` and run `/restart reload`.

Files in the folder are never overwritten by updates. A missing key falls back to English.

## Format

Texts use [MiniMessage](https://docs.advntr.dev/minimessage/format.html), so colors and formatting are written like `<yellow>` and `<bold>`. A line break in the kick message is `\n`.

`<prefix>` inserts the `prefix` text and can be used in any message.

## Placeholders

| Placeholder | Meaning |
|---|---|
| `<time>` | Remaining time, short form like `5m 30s` |
| `<clock>` | Remaining time as a clock like `05:30` |
| `<seconds>` | Remaining seconds, used in the final countdown |
| `<reason>` | The reason |
| `<player>` | Who cancelled the restart |
| `<label>` | The command that was typed |
| `<input>` | The time that could not be understood |
| `<max>` | The longest allowed time |
| `<old>` and `<new>` | Remaining time before and after a change |
| `<when>` | Date and time of the next automatic restart |

Keys that end with `-reason` are used instead of the normal key when a reason was given. Keep the placeholders of the English file in your texts.
