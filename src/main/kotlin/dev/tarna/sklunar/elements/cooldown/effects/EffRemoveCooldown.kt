package dev.tarna.sklunar.elements.cooldown.effects

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
import com.lunarclient.apollo.module.cooldown.CooldownModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event

@Name("Remove Cooldown")
@Description("Remove a Lunar cooldown for a player")
@Examples("remove the cooldown with id \"example\" for player")
@Since("0.4.0")
class EffRemoveCooldown : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffRemoveCooldown::class.java, "remove [the] [lunar] cooldown with id %strings% for %apolloplayers%")
        }
    }

    lateinit var ids: Expression<String>
    lateinit var players: Expression<ApolloPlayer>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        ids = exprs[0] as Expression<String>
        players = exprs[1] as Expression<ApolloPlayer>
        return true
    }

    override fun execute(event: Event) {
        val ids = ids.getArray(event) ?: return
        val players = players.getArray(event) ?: return
        if (ids.isEmpty() || players.isEmpty()) return

        val cooldownModule = Apollo.getModuleManager().getModule(CooldownModule::class.java)
        ids.forEach { id ->
            cooldownModule.removeCooldown(Recipients.of(players.toList()), id)
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "remove the lunar cooldown with id ${ids.toString(event, debug)} for ${players.toString(event, debug)}"
    }
}