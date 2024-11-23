package dev.tarna.sklunar.elements.staff.effects

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
import com.lunarclient.apollo.module.staffmod.StaffModModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event

@Name("Enable/Disable Staff Mods")
@Description("Enable or disable Lunar staff mods for a player")
@Examples(
    "enable the lunar staff mods for player",
    "disable the lunar staff mods for player"
)
@Since("0.1.0")
class EffStaffMods : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffStaffMods::class.java, "(:enable|:disable) [the] [lunar] staff mods for %apolloplayers%")
        }
    }

    lateinit var players: Expression<ApolloPlayer>

    lateinit var action: String

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<ApolloPlayer>
        action = if (parseResult.hasTag("enable")) "enable"
        else if (parseResult.hasTag("disable")) "disable"
        else "enable"
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event) ?: return
        if (players.isEmpty()) return

        val staffModule = Apollo.getModuleManager().getModule(StaffModModule::class.java)
        when (action) {
            "enable" -> staffModule.enableAllStaffMods(Recipients.of(players.toList()))
            "disable" -> staffModule.disableAllStaffMods(Recipients.of(players.toList()))
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "${action}d the lunar staff mods for ${players.toString(event, debug)}"
    }
}