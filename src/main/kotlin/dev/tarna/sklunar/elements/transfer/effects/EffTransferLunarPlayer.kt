package dev.tarna.sklunar.elements.transfer.effects

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
import com.lunarclient.apollo.module.transfer.TransferModule
import com.lunarclient.apollo.module.transfer.TransferResponse
import com.lunarclient.apollo.player.ApolloPlayer
import dev.tarna.sklunar.api.lunar.events.BukkitApolloTransferAcceptEvent
import dev.tarna.sklunar.api.lunar.events.BukkitApolloTransferRejectEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event

@Name("Transfer Lunar Player")
@Description("Transfer a player to another server")
@Examples("transfer player to \"example.com\"")
@Since("0.1.0")
class EffTransferLunarPlayer : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffTransferLunarPlayer::class.java, "lunar transfer [player] %apolloplayers% to %string%")
        }
    }

    lateinit var players: Expression<ApolloPlayer>
    lateinit var server: Expression<String>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<ApolloPlayer>
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
            val bukkitPlayer = player.player as Player
            transferModule.transfer(player, server)
                .onSuccess { response ->
                    when (response.status) {
                        TransferResponse.Status.ACCEPTED -> {
                            pm.callEvent(BukkitApolloTransferAcceptEvent(bukkitPlayer, server))
                        }

                        TransferResponse.Status.REJECTED -> {
                            pm.callEvent(BukkitApolloTransferRejectEvent(bukkitPlayer, server))
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