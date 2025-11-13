package dev.tarna.sklunar.elements.tebex.effects

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
import com.lunarclient.apollo.module.tebex.TebexModule
import com.lunarclient.apollo.player.ApolloPlayer
import org.bukkit.event.Event

@Name("Tebex Checkout")
@Description("Open the Tebex embedded checkout for a player with a specific basket ID and optional locale.")
@Examples("open tebex checkout for player with basket id \"12345\" and locale \"en_US\"")
@Since("0.5.0")
class EffTebexCheckout : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffTebexCheckout::class.java, "open tebex checkout for %apolloplayers% with basket [id] %string% [and locale %-string%]")
        }
    }

    lateinit var players: Expression<ApolloPlayer>
    lateinit var basketId: Expression<String>
    var locale: Expression<String>? = null

    override fun init(exprs: Array<out Expression<*>?>, patern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<ApolloPlayer>
        basketId = exprs[1] as Expression<String>
        locale = exprs[2] as Expression<String>?
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        val basketId = basketId.getSingle(event) ?: return
        val locale = locale?.getSingle(event)

        val tebexModule = Apollo.getModuleManager().getModule(TebexModule::class.java)
        for (player in players) {
            tebexModule.displayTebexEmbeddedCheckout(player, basketId, locale)
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "open tebex checkout for ${players.toString(event, debug)} with basket id ${basketId.toString(event, debug)}" +
                if (locale != null) " and locale ${locale?.toString(event, debug)}" else ""
    }
}