package dev.tarna.sklunar.elements.other.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import org.bukkit.entity.Player
import org.bukkit.event.Event

class CondIsRunningLunar : Condition() {
    companion object {
        init {
            Skript.registerCondition(CondIsRunningLunar::class.java,
                "%player% (is|1:(isn't|is not)) running lunar [client]",
                "%player% (is|1:(isn't|is not)) (on|using) lunar [client]"
            )
        }
    }

    lateinit var player: Expression<Player>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        player = exprs[0] as Expression<Player>
        isNegated = parseResult.hasTag("1")
        return true
    }

    override fun check(event: Event): Boolean {
        val player = player.getSingle(event) ?: return false
        return isNegated != Apollo.getPlayerManager().hasSupport(player.uniqueId)
    }

    override fun toString(event: Event?, debug: Boolean): String {
        val neg = if (isNegated) "isn't" else "is"
        return "${player.getSingle(event)} $neg on Lunar Client"
    }
}