package dev.tarna.sklunar.elements.nametag.effects

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
import com.lunarclient.apollo.module.nametag.Nametag
import com.lunarclient.apollo.module.nametag.NametagModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.not
import org.bukkit.event.Event

@Name("Override Lunar Nametag")
@Description("Override the lunar nametag of a player. Supports multiple nametag lines.")
@Examples("set the lunar nametag of player to \"%player%\", \"$%player's money%\"")
@Since("0.1.0")
class EffOverrideNametag : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffOverrideNametag::class.java, "(override|set) [the] [lunar] name[ ]tag of %apolloplayers% (with|to) %strings% [for %apolloplayers%]")
        }
    }

    lateinit var players: Expression<ApolloPlayer>
    lateinit var nametag: Expression<String>
    lateinit var recipients: Expression<ApolloPlayer>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<ApolloPlayer>
        nametag = exprs[1] as Expression<String>
        recipients = exprs[2] as Expression<ApolloPlayer>
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        val nametag = nametag.getArray(event) ?: return
        if (players.isEmpty() || nametag.isEmpty()) return

        val recipients = if (recipients.getArray(event) != null) Recipients.of(recipients.getArray(event).toList()) else Recipients.ofEveryone()

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