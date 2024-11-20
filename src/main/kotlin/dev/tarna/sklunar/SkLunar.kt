package dev.tarna.sklunar

import dev.tarna.sklunar.api.util.Util
import org.bukkit.plugin.java.JavaPlugin

class SkLunar : JavaPlugin() {
    lateinit var addonLoader: AddonLoader

    override fun onEnable() {
        val start = System.currentTimeMillis()

        addonLoader = AddonLoader(this)
        if (!addonLoader.canLoad()) {
            server.pluginManager.disablePlugin(this)
            return
        }

        Util.log("Enabled in ${System.currentTimeMillis() - start}ms")
    }

    override fun onDisable() {
        val start = System.currentTimeMillis()
        Util.log("Disabled in ${System.currentTimeMillis() - start}ms")
    }
}