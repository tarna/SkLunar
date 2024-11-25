package dev.tarna.sklunar.elements.limb.effects

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
import com.lunarclient.apollo.module.limb.BodyPart
import com.lunarclient.apollo.module.limb.LimbModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event

@Name("Hide Body Part")
@Description("Hide body parts from players")
@Examples("hide all body parts of player from all players")
@Since("0.4.0")
class EffHideBodyPart : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffHideBodyPart::class.java, "hide %bodyparts% of %apolloplayers% from %apolloplayers%")
        }
    }

    lateinit var bodyParts: Expression<BodyPart>
    lateinit var players: Expression<ApolloPlayer>
    lateinit var viewers: Expression<ApolloPlayer>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        bodyParts = exprs[0] as Expression<BodyPart>
        players = exprs[1] as Expression<ApolloPlayer>
        viewers = exprs[2] as Expression<ApolloPlayer>
        return true
    }

    override fun execute(event: Event) {
        val bodyParts = bodyParts.getArray(event) ?: return
        val players = players.getArray(event) ?: return
        val viewers = viewers.getArray(event) ?: return
        if (players.isEmpty() || viewers.isEmpty()) return

        val limbModule  = Apollo.getModuleManager().getModule(LimbModule::class.java)
        val recipients = Recipients.of(viewers.toList())
        for (player in players) {
            limbModule.hideBodyParts(recipients, player.uniqueId, bodyParts.toList())
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "hide ${bodyParts.toString(event, debug)} of ${players.toString(event, debug)} from ${viewers.toString(event, debug)}"
    }
}