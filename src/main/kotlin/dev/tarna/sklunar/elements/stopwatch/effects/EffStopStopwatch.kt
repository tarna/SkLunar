package dev.tarna.sklunar.elements.stopwatch.effects

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
import com.lunarclient.apollo.module.stopwatch.StopwatchModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event

@Name("Stop Stopwatch")
@Description("Stop the Lunar stopwatch for a player")
@Examples("stop the lunar stopwatch for player")
@Since("0.1.0")
class EffStopStopwatch : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffStopStopwatch::class.java, "stop [the] [lunar] stopwatch for %apolloplayers%")
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

        val stopwatchModule = Apollo.getModuleManager().getModule(StopwatchModule::class.java)
        stopwatchModule.stopStopwatch(Recipients.of(players.toList()))
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "stop the lunar stopwatch for ${players.toString(event, debug)}"
    }
}