package dev.tarna.sklunar.elements.nametag.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.nametag.Nametag
import com.lunarclient.apollo.module.nametag.NametagModule
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.not
import dev.tarna.sklunar.api.util.toApollo
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffOverrideNametag : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffOverrideNametag::class.java, "override [the] [lunar] name[ ]tag of %players% with %strings% [for %players%]")
        }
    }

    lateinit var players: Expression<Player>
    lateinit var nametag: Expression<String>
    lateinit var recipients: Expression<Player>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<Player>
        nametag = exprs[1] as Expression<String>
        recipients = exprs[2] as Expression<Player>
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        val nametag = nametag.getArray(event) ?: return
        if (players.isEmpty() || nametag.isEmpty()) return

        val recipients = if (recipients.getArray(event) != null) Recipients.of(recipients.getArray(event).toList().toApollo()) else Recipients.ofEveryone()

        val nametagModule = Apollo.getModuleManager().getModule(NametagModule::class.java)
        for (player in players) {
            nametagModule.overrideNametag(
                recipients,
                player.uniqueId,
                Nametag.builder()
                    .lines(!nametag.toMutableList().reversed())
                    .build()
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "override the lunar nametag of ${players.toString(event, debug)} with ${nametag.toString(event, debug)}"
    }
}