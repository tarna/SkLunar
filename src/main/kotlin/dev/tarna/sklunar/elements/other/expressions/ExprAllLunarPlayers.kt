package dev.tarna.sklunar.elements.other.expressions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.player.ApolloPlayer
import org.bukkit.event.Event

@Name("All Lunar Players")
@Description("Get all Lunar players")
@Examples("send \"There are %size of all lunar players% Lunar players online!\"")
@Since("0.1.0")
class ExprAllLunarPlayers : SimpleExpression<ApolloPlayer>() {
    companion object {
        init {
            Skript.registerExpression(ExprAllLunarPlayers::class.java, ApolloPlayer::class.java, ExpressionType.SIMPLE,
                "[all] lunar [client] players",
                "[all] players using lunar [client]"
            )
        }
    }

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        return true
    }

    override fun get(event: Event): Array<ApolloPlayer> {
        return Apollo.getPlayerManager().players.toList().toTypedArray()
    }

    override fun isSingle(): Boolean {
        return false
    }

    override fun getReturnType(): Class<out ApolloPlayer> {
        return ApolloPlayer::class.java
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "all lunar players"
    }
}