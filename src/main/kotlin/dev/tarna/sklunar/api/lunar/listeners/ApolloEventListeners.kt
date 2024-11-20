package dev.tarna.sklunar.api.lunar.listeners

import com.lunarclient.apollo.event.ApolloListener
import com.lunarclient.apollo.event.EventBus
import com.lunarclient.apollo.event.Listen
import com.lunarclient.apollo.event.player.ApolloRegisterPlayerEvent
import com.lunarclient.apollo.event.player.ApolloUnregisterPlayerEvent
import dev.tarna.sklunar.api.lunar.events.BukkitApolloRegisterPlayerEvent
import dev.tarna.sklunar.api.lunar.events.BukkitApolloUnregisterPlayerEvent
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginManager

class ApolloEventListeners(val pluginManager: PluginManager) : ApolloListener {
    init {
        EventBus.getBus().register(this)
    }

    @Listen
    fun onApolloRegister(event: ApolloRegisterPlayerEvent) {
        pluginManager.callEvent(BukkitApolloRegisterPlayerEvent(event.player.player as? Player? ?: return))
    }

    @Listen
    fun onApolloUnregister(event: ApolloUnregisterPlayerEvent) {
        pluginManager.callEvent(BukkitApolloUnregisterPlayerEvent(event.player.player as? Player? ?: return))
    }
}