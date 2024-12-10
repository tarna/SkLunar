package dev.tarna.sklunar.elements.fire.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.skript.util.SkriptColor
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.coloredfire.ColoredFireModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event
import java.awt.Color

@Name("Override Colored Fire")
@Description("Override the colored fire of a player")
@Examples("set the colored fire of player to red")
@Since("0.1.0")
class EffOverrideColoredFire : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffOverrideColoredFire::class.java, "(override|set) [the] colored fire of %apolloplayers% to %color% [for %-apolloplayers%]")
        }
    }

    lateinit var players: Expression<ApolloPlayer>
    lateinit var color: Expression<SkriptColor>
    var targetPlayers: Expression<ApolloPlayer>? = null

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<ApolloPlayer>
        color = exprs[1] as Expression<SkriptColor>
        targetPlayers = exprs[2] as Expression<ApolloPlayer>?
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        val color = color.getSingle(event) ?: return
        val targetPlayers = targetPlayers?.getArray(event)
        val targetRecipients = if (targetPlayers != null) Recipients.of(targetPlayers.toList()) else Recipients.ofEveryone()

        val fireModule = Apollo.getModuleManager().getModule(ColoredFireModule::class.java)
        for (player in players) {
            fireModule.overrideColoredFire(
                targetRecipients,
                player.uniqueId,
                Color(
                    color.asBukkitColor().red,
                    color.asBukkitColor().green,
                    color.asBukkitColor().blue
                )
            )
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "override the colored fire of ${players.toString(e, debug)} to ${color.toString(e, debug)} for ${targetPlayers?.toString(e, debug)}"
    }
}