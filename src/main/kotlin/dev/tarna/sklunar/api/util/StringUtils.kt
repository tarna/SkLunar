package dev.tarna.sklunar.api.util

import net.kyori.adventure.text.minimessage.MiniMessage

val mm = MiniMessage.miniMessage()

operator fun String.not() = mm.deserialize(this.legacyToMiniMessage())

operator fun List<String>.not() = map { !it }

val legacy = mapOf(
    "&0" to "<black>",
    "&1" to "<dark_blue>",
    "&2" to "<dark_green>",
    "&3" to "<dark_aqua>",
    "&4" to "<dark_red>",
    "&5" to "<dark_purple>",
    "&6" to "<gold>",
    "&7" to "<gray>",
    "&8" to "<dark_gray>",
    "&9" to "<blue>",
    "&a" to "<green>",
    "&b" to "<aqua>",
    "&c" to "<red>",
    "&d" to "<light_purple>",
    "&e" to "<yellow>",
    "&f" to "<white>",
    "&k" to "<obfuscated>",
    "&l" to "<bold>",
    "&m" to "<strikethrough>",
    "&n" to "<underline>",
    "&o" to "<italic>",
    "&r" to "<reset>"
)

fun String.legacyToMiniMessage(): String {
    var message = this
    for ((key, value) in legacy) {
        message = message.replace(key, value)
    }
    return message
}