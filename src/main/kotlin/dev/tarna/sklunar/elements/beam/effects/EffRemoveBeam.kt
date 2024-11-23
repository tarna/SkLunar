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

@Name("Remove Beam")
@Description("Remove a beam from a player")
@Examples("remove beam with id \"example\" for player")
@Since("0.1.0")
class EffRemoveBeam : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffRemoveBeam::class.java, "remove [the] beam with id %string% for %apolloplayers%")
        }
    }

    lateinit var id: Expression<String>
    lateinit var players: Expression<ApolloPlayer>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        id = exprs[0] as Expression<String>
        players = exprs[1] as Expression<ApolloPlayer>
        return true
    }

    override fun execute(event: Event) {
        val id = id.getSingle(event) ?: return
        val players = players.getArray(event) ?: return
        if (players.isEmpty()) return

        val beamModule = Apollo.getModuleManager().getModule(BeamModule::class.java)
        val apolloRecipients = Recipients.of(players.toList())
        beamModule.removeBeam(apolloRecipients, id)
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "remove beam with id ${id.toString(e, debug)} for ${players.toString(e, debug)}"
    }
}