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

@Name("Override Lunar Nick")
@Description("Override the nick of a player")
@Examples("override the nick of player to \"Nerd\"")
@Since("0.2.0")
class EffOverrideNick : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffOverrideNick::class.java, "(override|set) [the] nick of %apolloplayers% to %string%")
        }
    }

    lateinit var players: Expression<ApolloPlayer>
    lateinit var nick: Expression<String>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<ApolloPlayer>
        nick = exprs[1] as Expression<String>
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        val nick = nick.getSingle(event) ?: return
        if (players.isEmpty()) return

        val nickModule = Apollo.getModuleManager().getModule(NickHiderModule::class.java)
        val recipients = Recipients.of(players.toList())
        nickModule.overrideNick(recipients, nick)
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "override the nick of ${players.toString(event, debug)} to ${nick.toString(event, debug)}"
    }
}