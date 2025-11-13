package dev.tarna.sklunar.elements.combat.effects

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
import com.lunarclient.apollo.module.combat.CombatModule
import org.bukkit.event.Event

@Name("Miss Penalty")
@Description("Enable or disable the miss penalty.")
@Examples(
    "enable miss penalty",
    "disable miss penalty"
)
@Since("0.5.0")
class EffMissPenalty : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffMissPenalty::class.java,
                "(:enable|disable) miss penalty"
            )
        }
    }

    var enable = false

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        enable = parseResult.hasTag("enable")
        return true
    }

    override fun execute(event: Event) {
        val combatModule = Apollo.getModuleManager().getModule(CombatModule::class.java)
        combatModule.options.set(CombatModule.DISABLE_MISS_PENALTY, !enable)
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "${if (enable) "enable" else "disable"} miss penalty"
    }
}