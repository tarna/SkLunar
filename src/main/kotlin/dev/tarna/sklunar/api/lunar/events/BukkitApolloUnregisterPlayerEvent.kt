package dev.tarna.sklunar.api.lunar.events

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class BukkitApolloUnregisterPlayerEvent(player: Player) : PlayerEvent(player) {
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}