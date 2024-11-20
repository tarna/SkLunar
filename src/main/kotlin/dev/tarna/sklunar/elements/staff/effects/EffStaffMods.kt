package dev.tarna.sklunar.elements.staff.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.module.staffmod.StaffModModule
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.toApollo
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffStaffMods : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffStaffMods::class.java, "(:enable|:disable) [the] [lunar] staff mods for %players%")
        }
    }

    lateinit var players: Expression<Player>

    lateinit var action: String

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        players = exprs[0] as Expression<Player>
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
            "enable" -> staffModule.enableAllStaffMods(Recipients.of(players.toList().toApollo()))
            "disable" -> staffModule.disableAllStaffMods(Recipients.of(players.toList().toApollo()))
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "${action}d the lunar staff mods for ${players.toString(event, debug)}"
    }
}