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

class EffDisplayLiveChatMessage : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffDisplayLiveChatMessage::class.java, "(display|show) live [chat] message %string% with id %integer% to %players%")
        }
    }

    lateinit var message: Expression<String>
    lateinit var id: Expression<Int>
    lateinit var players: Expression<Player>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        message = exprs[0] as Expression<String>
        id = exprs[1] as Expression<Int>
        players = exprs[2] as Expression<Player>
        return true
    }

    override fun execute(event: Event) {
        val message = message.getSingle(event) ?: return
        val id = id.getSingle(event) ?: return
        val players = players.getArray(event) ?: return
        if (players.isEmpty()) return

        val chatModule = Apollo.getModuleManager().getModule(ChatModule::class.java)
        chatModule.displayLiveChatMessage(
            Recipients.of(players.toList().toApollo()),
            !message,
            id
        )
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "display live chat message ${message.toString(event, debug)} with id ${id.toString(event, debug)} to ${players.toString(event, debug)}"
    }
}