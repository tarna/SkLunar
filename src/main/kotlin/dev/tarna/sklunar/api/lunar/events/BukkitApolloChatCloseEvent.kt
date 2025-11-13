package dev.tarna.sklunar.api.lunar.events

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

class BukkitApolloChatCloseEvent(player: Player) : PlayerEvent(player) {
    companion object {
        @JvmStatic
        val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}