package dev.tarna.sklunar.api.util

import org.bukkit.Bukkit

object Util {
    private val prefix = "<gray>[<aqua>Sk<green>Lunar<gray>] <reset>"
    private val prefix_error = "<gray>[<aqua>Sk<green>Lunar <red>ERROR<gray>] <reset>"

    fun log(message: String) {
        Bukkit.getConsoleSender().send("$prefix$message")
    }
}