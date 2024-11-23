package dev.tarna.sklunar.elements.nick.effects

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
import com.lunarclient.apollo.module.nickhider.NickHiderModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event

@Name("Reset Lunar Nick")
@Description("Reset the nick of a player")
@Examples("reset the lunar nick of player")
@Since("0.2.0")
class EffResetNick : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffResetNick::class.java, "reset [the] [lunar] nick of %apolloplayers%")
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

        val nickModule = Apollo.getModuleManager().getModule(NickHiderModule::class.java)
        val recipients = Recipients.of(players.toList())
        nickModule.resetNick(recipients)
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "reset the nick of ${players.toString(event, debug)}"
    }
}