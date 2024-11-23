package dev.tarna.sklunar.elements.chat.effects

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
import com.lunarclient.apollo.module.chat.ChatModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.not
import org.bukkit.event.Event

@Name("Display Live Chat Message")
@Description("Display a live chat message to a player")
@Examples(
    "loop 5 times",
    "\tshow live chat message \"%loop-value%\" with id 1 to player",
    "\twait 1 second"
)
@Since("0.1.0")
class EffDisplayLiveChatMessage : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffDisplayLiveChatMessage::class.java, "(display|show) live [chat] message %string% with id %integer% to %apolloplayers%")
        }
    }

    lateinit var message: Expression<String>
    lateinit var id: Expression<Int>
    lateinit var players: Expression<ApolloPlayer>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        message = exprs[0] as Expression<String>
        id = exprs[1] as Expression<Int>
        players = exprs[2] as Expression<ApolloPlayer>
        return true
    }

    override fun execute(event: Event) {
        val message = message.getSingle(event) ?: return
        val id = id.getSingle(event) ?: return
        val players = players.getArray(event) ?: return
        if (players.isEmpty()) return

        val chatModule = Apollo.getModuleManager().getModule(ChatModule::class.java)
        chatModule.displayLiveChatMessage(
            Recipients.of(players.toList()),
            !message,
            id
        )
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "display live chat message ${message.toString(event, debug)} with id ${id.toString(event, debug)} to ${players.toString(event, debug)}"
    }
}