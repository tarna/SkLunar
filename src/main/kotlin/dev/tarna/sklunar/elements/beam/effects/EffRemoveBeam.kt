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

class EffRemoveBeam : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffRemoveBeam::class.java, "remove [the] beam with id %string% for %players%")
        }
    }

    lateinit var id: Expression<String>
    lateinit var players: Expression<Player>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        id = exprs[0] as Expression<String>
        players = exprs[1] as Expression<Player>
        return true
    }

    override fun execute(event: Event) {
        val id = id.getSingle(event) ?: return
        val players = players.getArray(event) ?: return
        if (players.isEmpty()) return

        val beamModule = Apollo.getModuleManager().getModule(BeamModule::class.java)
        val apolloRecipients = Recipients.of(players.toList().toApollo())
        beamModule.removeBeam(apolloRecipients, id)
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "remove beam with id ${id.toString(e, debug)} for ${players.toString(e, debug)}"
    }
}