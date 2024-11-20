package dev.tarna.sklunar.elements.beam.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.skript.util.SkriptColor
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.beam.Beam
import com.lunarclient.apollo.module.beam.BeamModule
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.toApollo
import dev.tarna.sklunar.api.util.toApolloBlockLocation
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Event
import java.awt.Color

class EffDisplayBeam : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffDisplayBeam::class.java, "(create|display) [a] %color% beam at %location% with id %string% to %players%")
        }
    }

    lateinit var color: Expression<SkriptColor>
    lateinit var location: Expression<Location>
    lateinit var id: Expression<String>
    lateinit var players: Expression<Player>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        color = exprs[0] as Expression<SkriptColor>
        location = exprs[1] as Expression<Location>
        id = exprs[2] as Expression<String>
        players = exprs[3] as Expression<Player>
        return true
    }

    override fun execute(event: Event) {
        val color = color.getSingle(event) ?: return
        val location = location.getSingle(event) ?: return
        val id = id.getSingle(event) ?: return
        val players = players.getArray(event) ?: return
        if (players.isEmpty()) return

        val beamModule = Apollo.getModuleManager().getModule(BeamModule::class.java)
        val apolloRecipients = Recipients.of(players.toList().toApollo())
        beamModule.displayBeam(apolloRecipients, Beam.builder()
            .id(id)
            .color(Color(
                color.asBukkitColor().red,
                color.asBukkitColor().green,
                color.asBukkitColor().blue
            ))
            .location(location.toApolloBlockLocation())
            .build()
        )
        println("Color: $color")
        println("Location: ${location.x}, ${location.y}, ${location.z}, ${location.world.name}")
        println("ID: $id")
        println("Players: ${players.joinToString(", ") { it.name }}")
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "display ${color.toString(event, debug)} beam at ${location.toString(event, debug)} with id ${id.toString(event, debug)} to ${players.toString(event, debug)}"
    }
}