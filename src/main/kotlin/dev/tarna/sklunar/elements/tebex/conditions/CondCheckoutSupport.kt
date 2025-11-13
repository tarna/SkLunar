package dev.tarna.sklunar.elements.tebex.conditions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.module.tebex.TebexEmbeddedCheckoutSupport
import com.lunarclient.apollo.player.ApolloPlayer
import org.bukkit.event.Event

class CondCheckoutSupport : Condition() {
    companion object {
        init {
            Skript.registerCondition(CondCheckoutSupport::class.java,
                "%apolloplayer% (has|1:(doesn't|does not) have) tebex checkout support for %tebexcheckoutsupport%",
                "%apolloplayer% (has|1:(doesn't|does not) have) %tebexcheckoutsupport% tebex checkout support"
            )
        }
    }

    lateinit var player: Expression<ApolloPlayer>
    lateinit var checkoutSupport: Expression<TebexEmbeddedCheckoutSupport>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        player = exprs[0] as Expression<ApolloPlayer>
        checkoutSupport = exprs[1] as Expression<TebexEmbeddedCheckoutSupport>
        isNegated = parseResult.hasTag("1")
        return true
    }

    override fun check(event: Event): Boolean {
        val player = player.getSingle(event) ?: return false
        val embeddedCheckoutSupport = player.tebexEmbeddedCheckoutSupport
        val requiredSupport = checkoutSupport.getSingle(event) ?: return false
        val hasSupport = embeddedCheckoutSupport == requiredSupport
        return isNegated != hasSupport
    }

    override fun toString(event: Event?, debug: Boolean): String {
        val neg = if (isNegated) "doesn't have" else "has"
        return "${player.getSingle(event)} $neg tebex checkout support for ${checkoutSupport.getSingle(event)}"
    }
}