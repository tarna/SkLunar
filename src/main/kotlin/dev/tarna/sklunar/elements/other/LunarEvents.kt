package dev.tarna.sklunar.elements.other

import ch.njol.skript.Skript
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
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

            EventValues.registerEventValue(
                BukkitApolloRegisterPlayerEvent::class.java,
                Player::class.java,
                object : Getter<Player, BukkitApolloRegisterPlayerEvent>() {
                    override fun get(event: BukkitApolloRegisterPlayerEvent): Player {
                        return event.player
                    }
                }, EventValues.TIME_NOW)

            Skript.registerEvent(
                "Apollo Unregister Player",
                LunarEvents::class.java,
                BukkitApolloUnregisterPlayerEvent::class.java,
                "[lunar|apollo] unregister player",
                "lunar player quit"
            )

            EventValues.registerEventValue(
                BukkitApolloUnregisterPlayerEvent::class.java,
                Player::class.java,
                object : Getter<Player, BukkitApolloUnregisterPlayerEvent>() {
                    override fun get(event: BukkitApolloUnregisterPlayerEvent): Player {
                        return event.player
                    }
            }, EventValues.TIME_NOW)
        }
    }
}