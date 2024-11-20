package dev.tarna.sklunar.elements.beam.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.beam.BeamModule
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.toApollo
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffResetBeams : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffResetBeams::class.java, "reset [all] beams for %players%")
        }
    }

    lateinit var players: Expression<Player>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<Player>
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return

        if (players.isEmpty()) return

        val beamModule = Apollo.getModuleManager().getModule(BeamModule::class.java)
        val apolloRecipients = Recipients.of(players.toList().toApollo())
        beamModule.resetBeams(apolloRecipients)
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "reset all beams for ${players.toString(e, debug)}"
    }
}