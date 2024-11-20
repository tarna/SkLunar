package dev.tarna.sklunar.elements.other.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import dev.tarna.sklunar.api.util.toPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event

class ExprAllLunarPlayers : SimpleExpression<Player>() {
    companion object {
        init {
            Skript.registerExpression(ExprAllLunarPlayers::class.java, Player::class.java, ExpressionType.SIMPLE, "all lunar players")
        }
    }

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        return true
    }

    override fun get(event: Event): Array<Player?> {
        return Apollo.getPlayerManager().players.toList().toPlayer().toTypedArray()
    }

    override fun isSingle(): Boolean {
        return false
    }

    override fun getReturnType(): Class<out Player> {
        return Player::class.java
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "all lunar players"
    }
}