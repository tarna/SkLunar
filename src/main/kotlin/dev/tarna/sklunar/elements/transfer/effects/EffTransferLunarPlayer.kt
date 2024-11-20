package dev.tarna.sklunar.elements.transfer.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.transfer.TransferModule
import com.lunarclient.apollo.module.transfer.TransferResponse
import dev.tarna.sklunar.api.lunar.events.BukkitApolloTransferAcceptEvent
import dev.tarna.sklunar.api.lunar.events.BukkitApolloTransferRejectEvent
import dev.tarna.sklunar.api.util.toApollo
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffTransferLunarPlayer : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffTransferLunarPlayer::class.java, "lunar transfer [player] %players% to %string%")
        }
    }

    lateinit var players: Expression<Player>
    lateinit var server: Expression<String>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<Player>
        server = exprs[1] as Expression<String>
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        val server = server.getSingle(event) ?: return
        if (players.isEmpty()) return

        val transferModule = Apollo.getModuleManager().getModule(TransferModule::class.java)
        val pm = Bukkit.getPluginManager()
        for (player in players) {
            transferModule.transfer(player.toApollo(), server)
                .onSuccess { response ->
                    when (response.status) {
                        TransferResponse.Status.ACCEPTED -> {
                            pm.callEvent(BukkitApolloTransferAcceptEvent(player, server))
                        }

                        TransferResponse.Status.REJECTED -> {
                            pm.callEvent(BukkitApolloTransferRejectEvent(player, server))
                        }

                        else -> {}
                    }
                }
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "lunar transfer player ${players.toString(event, debug)} to ${server.toString(event, debug)}"
    }
}