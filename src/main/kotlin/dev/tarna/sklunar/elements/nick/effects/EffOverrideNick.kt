package dev.tarna.sklunar.elements.nick.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.nickhider.NickHiderModule
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.toApollo
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffOverrideNick : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffOverrideNick::class.java, "(override|set) [the] nick of %players% to %string%")
        }
    }

    lateinit var players: Expression<Player>
    lateinit var nick: Expression<String>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<Player>
        nick = exprs[1] as Expression<String>
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        val nick = nick.getSingle(event) ?: return
        if (players.isEmpty()) return

        val nickModule = Apollo.getModuleManager().getModule(NickHiderModule::class.java)
        val recipients = Recipients.of(players.toList().toApollo())
        nickModule.overrideNick(recipients, nick)
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "override the nick of ${players.toString(event, debug)} to ${nick.toString(event, debug)}"
    }
}