package dev.tarna.sklunar.elements.chat.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.chat.ChatModule
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.not
import dev.tarna.sklunar.api.util.toApollo
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffRemoveLiveChatMessage : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffRemoveLiveChatMessage::class.java, "remove live [chat] message with id %integer% (from|for) %players%")
        }
    }

    lateinit var id: Expression<Int>
    lateinit var players: Expression<Player>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        id = exprs[0] as Expression<Int>
        players = exprs[1] as Expression<Player>
        return true
    }

    override fun execute(event: Event) {
        val id = id.getSingle(event) ?: return
        val players = players.getArray(event) ?: return
        if (players.isEmpty()) return

        val chatModule = Apollo.getModuleManager().getModule(ChatModule::class.java)
        chatModule.removeLiveChatMessage(
            Recipients.of(players.toList().toApollo()),
            id
        )
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "remove live chat message with id ${id.toString(event, debug)} from ${players.toString(event, debug)}"
    }
}