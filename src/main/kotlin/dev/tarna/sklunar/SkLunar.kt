package dev.tarna.sklunar

import ch.njol.skript.Skript
import dev.tarna.sklunar.api.util.Util
import org.bstats.bukkit.Metrics
import org.bstats.charts.SimplePie
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

        loadMetrics()

        Util.log("Enabled in ${System.currentTimeMillis() - start}ms")
    }

    override fun onDisable() {
        val start = System.currentTimeMillis()
        Util.log("Disabled in ${System.currentTimeMillis() - start}ms")
    }

    private fun loadMetrics() {
        val metrics = Metrics(this, 23951)
        metrics.addCustomChart(SimplePie("skript_version") {
            Skript.getVersion().toString()
        })
        Util.log("Loaded bStats")
    }
}