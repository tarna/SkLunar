package dev.tarna.sklunar.elements.fire.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.skript.util.SkriptColor
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.coloredfire.ColoredFireModule
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.toApollo
import org.bukkit.entity.Player
import org.bukkit.event.Event
import java.awt.Color

class EffOverrideColoredFire : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffOverrideColoredFire::class.java, "(override|set) [the] colored fire of %players% to %color% [for %players%]")
        }
    }

    lateinit var players: Expression<Player>
    lateinit var color: Expression<SkriptColor>
    lateinit var targetPlayers: Expression<Player>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<Player>
        color = exprs[1] as Expression<SkriptColor>
        targetPlayers = exprs[2] as Expression<Player>
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        val color = color.getSingle(event) ?: return
        val targetPlayers = targetPlayers.getArray(event)
        val targetRecipients = if (targetPlayers != null) Recipients.of(targetPlayers.toList().toApollo()) else Recipients.ofEveryone()

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
        return "override the colored fire of ${players.toString(e, debug)} to ${color.toString(e, debug)} for ${targetPlayers.toString(e, debug)}"
    }
}