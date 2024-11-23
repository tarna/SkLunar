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
import org.bukkit.event.Event

@Name("Remove Live Chat Message")
@Description("Remove a live chat message from a player")
@Examples("remove live chat message with id 1 from player")
@Since("0.1.0")
class EffRemoveLiveChatMessage : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffRemoveLiveChatMessage::class.java, "remove live [chat] message with id %integer% (from|for) %apolloplayers%")
        }
    }

    lateinit var id: Expression<Int>
    lateinit var players: Expression<ApolloPlayer>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        id = exprs[0] as Expression<Int>
        players = exprs[1] as Expression<ApolloPlayer>
        return true
    }

    override fun execute(event: Event) {
        val id = id.getSingle(event) ?: return
        val players = players.getArray(event) ?: return
        if (players.isEmpty()) return

        val chatModule = Apollo.getModuleManager().getModule(ChatModule::class.java)
        chatModule.removeLiveChatMessage(
            Recipients.of(players.toList()),
            id
        )
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "remove live chat message with id ${id.toString(event, debug)} from ${players.toString(event, debug)}"
    }
}