package dev.tarna.sklunar.elements.transfer.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import dev.tarna.sklunar.api.lunar.events.BukkitApolloTransferAcceptEvent
import org.bukkit.entity.Player

class TransferEvents : SimpleEvent() {
    companion object {
        init {
            Skript.registerEvent(
                "Lunar Transfer Player Accept",
                TransferEvents::class.java,
                BukkitApolloTransferAcceptEvent::class.java,
                "[lunar|apollo] transfer player accept",
            )
                .description("Called when a player accepts a transfer request")
                .examples(
                    "on lunar transfer player accept:",
                    "\tsend \"%player% has accepted the transfer request!\" to console"
                )
                .since("0.1.0")

            EventValues.registerEventValue(
                BukkitApolloTransferAcceptEvent::class.java,
                Player::class.java,
                object : Getter<Player, BukkitApolloTransferAcceptEvent>() {
                    override fun get(event: BukkitApolloTransferAcceptEvent): Player {
                        return event.player
                    }
            }, EventValues.TIME_NOW)

            EventValues.registerEventValue(
                BukkitApolloTransferAcceptEvent::class.java,
                String::class.java,
                object : Getter<String, BukkitApolloTransferAcceptEvent>() {
                    override fun get(event: BukkitApolloTransferAcceptEvent): String {
                        return event.server
                    }
            }, EventValues.TIME_NOW)

            Skript.registerEvent(
                "Lunar Transfer Player Reject",
                TransferEvents::class.java,
                BukkitApolloTransferAcceptEvent::class.java,
                "[lunar|apollo] transfer player (reject|deny)",
            )
                .description("Called when a player rejects a transfer request")
                .examples(
                    "on lunar transfer player reject:",
                    "\tsend \"%player% has rejected the transfer request!\" to console"
                )
                .since("0.1.0")

            EventValues.registerEventValue(
                BukkitApolloTransferAcceptEvent::class.java,
                Player::class.java,
                object : Getter<Player, BukkitApolloTransferAcceptEvent>() {
                    override fun get(event: BukkitApolloTransferAcceptEvent): Player {
                        return event.player
                    }
            }, EventValues.TIME_NOW)

            EventValues.registerEventValue(
                BukkitApolloTransferAcceptEvent::class.java,
                String::class.java,
                object : Getter<String, BukkitApolloTransferAcceptEvent>() {
                    override fun get(event: BukkitApolloTransferAcceptEvent): String {
                        return event.server
                    }
            }, EventValues.TIME_NOW)
        }
    }
}