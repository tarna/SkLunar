package dev.tarna.sklunar

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import dev.tarna.sklunar.api.lunar.listeners.ApolloEventListeners
import dev.tarna.sklunar.api.util.SkriptUtils
import dev.tarna.sklunar.api.util.Util

class AddonLoader(val plugin: SkLunar) {
    val pluginManager = plugin.server.pluginManager
    val skriptPlugin = pluginManager.getPlugin("Skript")
    val apolloPlugin = pluginManager.getPlugin("Apollo-Bukkit")

    lateinit var addon: SkriptAddon

    fun canLoad(): Boolean {
        if (apolloPlugin == null) {
            Util.log("<red>Dependency Apollo not found, plugin disabling...")
            return false
        }
        if (!apolloPlugin.isEnabled) {
            Util.log("<red>Dependency Apollo is not enabled, plugin disabling...")
            return false
        }
        if (skriptPlugin == null) {
            Util.log("<red>Dependency Skript not found, plugin disabling...")
            return false
        }
        if (!skriptPlugin.isEnabled) {
            Util.log("<red>Dependency Skript is not enabled, plugin disabling...")
            return false
        }
        if (!Skript.isAcceptRegistrations()) {
            Util.log("<red>Skript is no longer accepting registrations, addons can no longer be loaded!")
            val plugman = pluginManager.getPlugin("PlugMan")
            if (plugman != null && plugman.isEnabled) {
                Util.log("<red>It appears you're running PlugMan.");
                Util.log("<red>If you're trying to reload/enable SkLunar with PlugMan.... you can't.");
                Util.log("<yellow>Please restart your server!");
            } else {
                Util.log("<red>No clue how this could happen.");
                Util.log("<red>Seems a plugin is delaying SkLunar loading, which is after Skript stops accepting registrations.");
            }
            return false
        }
        loadSkriptElements()
        return true
    }

    private fun loadSkriptElements() {
        addon = Skript.registerAddon(plugin)

        val elementCountBefore = SkriptUtils.getElementCount()

        addon.loadClasses("dev.tarna.sklunar.elements")
        ApolloEventListeners(pluginManager)

        val elementCountAfter = SkriptUtils.getElementCount()
        val finish = IntArray(elementCountBefore.size)
        var total = 0
        for (i in elementCountBefore.indices) {
            finish[i] = elementCountAfter[i] - elementCountBefore[i]
            total += finish[i]
        }
        val elementNames = arrayOf("event", "effect", "expression", "condition", "section")
        Util.log("Loaded ($total) elements:")
        for (i in finish.indices) {
            Util.log(" - ${finish[i]} ${elementNames[i]}${if (finish[i] == 1) "" else "s"}")
        }
    }
}