package dev.tarna.sklunar.elements.cooldown.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.cooldown.CooldownModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event

@Name("Reset Cooldowns")
@Description("Reset all of the Lunar cooldowns for a player")
@Examples("reset all of the lunar cooldowns for all players")
@Since("0.4.0")
class EffResetCooldowns : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffResetCooldowns::class.java, "reset all [[of] the] [lunar] cooldowns for %apolloplayers%")
        }
    }

    lateinit var players: Expression<ApolloPlayer>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<ApolloPlayer>
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        if (players.isEmpty()) return

        val cooldownModule = Apollo.getModuleManager().getModule(CooldownModule::class.java)
        cooldownModule.resetCooldowns(Recipients.of(players.toList()))
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "reset all of the lunar cooldowns for ${players.toString(event, debug)}"
    }
}