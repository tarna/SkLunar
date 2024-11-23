package dev.tarna.sklunar.elements.beam.effects

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
import com.lunarclient.apollo.module.beam.BeamModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event

@Name("Reset Beams")
@Description("Reset all beams for a player")
@Examples("reset all beams for player")
@Since("0.1.0")
class EffResetBeams : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffResetBeams::class.java, "reset [all] beams for %apolloplayers%")
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

        val beamModule = Apollo.getModuleManager().getModule(BeamModule::class.java)
        val apolloRecipients = Recipients.of(players.toList())
        beamModule.resetBeams(apolloRecipients)
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "reset all beams for ${players.toString(e, debug)}"
    }
}