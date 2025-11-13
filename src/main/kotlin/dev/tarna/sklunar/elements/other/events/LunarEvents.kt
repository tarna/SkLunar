package dev.tarna.sklunar.elements.other.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import dev.tarna.sklunar.api.lunar.events.BukkitApolloChatCloseEvent
import dev.tarna.sklunar.api.lunar.events.BukkitApolloChatOpenEvent
import dev.tarna.sklunar.api.lunar.events.BukkitApolloRegisterPlayerEvent
import dev.tarna.sklunar.api.lunar.events.BukkitApolloUnregisterPlayerEvent
import org.bukkit.entity.Player

class LunarEvents : SimpleEvent() {
    companion object {
        init {
            Skript.registerEvent(
                "Apollo Register Player",
                LunarEvents::class.java,
                BukkitApolloRegisterPlayerEvent::class.java,
                "[lunar|apollo] register player",
                "lunar player join"
            )
                .description("Called when a player joins the server with Lunar Client")
                .examples(
                    "on lunar player join:",
                    "\tgive 64 diamonds to player"
                )
                .since("0.1.0")

            EventValues.registerEventValue(
                BukkitApolloRegisterPlayerEvent::class.java,
                Player::class.java,
                BukkitApolloRegisterPlayerEvent::getPlayer
            )

            Skript.registerEvent(
                "Apollo Unregister Player",
                LunarEvents::class.java,
                BukkitApolloUnregisterPlayerEvent::class.java,
                "[lunar|apollo] unregister player",
                "lunar player quit"
            )
                .description("Called when a player leaves the server with Lunar Client")
                .examples(
                    "on lunar player quit:",
                    "\tremove 64 diamonds from player"
                )
                .since("0.1.0")

            EventValues.registerEventValue(
                BukkitApolloUnregisterPlayerEvent::class.java,
                Player::class.java,
                BukkitApolloUnregisterPlayerEvent::getPlayer
            )

            Skript.registerEvent(
                "Chat Open",
                LunarEvents::class.java,
                BukkitApolloChatOpenEvent::class.java,
                "[lunar|apollo] chat open"
            )
                .description("Called when a player opens the chat using Lunar Client")
                .examples(
                    "on lunar chat open:",
                    "\tsend \"You opened the chat!\" to player"
                )
                .since("0.5.0")

            EventValues.registerEventValue(
                BukkitApolloChatOpenEvent::class.java,
                Player::class.java,
                BukkitApolloChatOpenEvent::getPlayer
            )

            Skript.registerEvent(
                "Chat Close",
                LunarEvents::class.java,
                BukkitApolloChatCloseEvent::class.java,
                "[lunar|apollo] chat close"
            )
                .description("Called when a player closes the chat using Lunar Client")
                .examples(
                    "on lunar chat close:",
                    "\tsend \"You closed the chat!\" to player"
                )
                .since("0.5.0")

            EventValues.registerEventValue(
                BukkitApolloChatCloseEvent::class.java,
                Player::class.java,
                BukkitApolloChatCloseEvent::getPlayer
            )
        }
    }
}