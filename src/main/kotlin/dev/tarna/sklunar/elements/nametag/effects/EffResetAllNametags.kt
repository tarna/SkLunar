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
import com.lunarclient.apollo.module.nametag.NametagModule
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event

@Name("Reset All Lunar Nametags")
@Description("Reset all Lunar nametags")
@Examples("reset all lunar nametags")
@Since("0.1.0")
class EffResetAllNametags : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffResetAllNametags::class.java, "reset all [lunar] name[ ]tags")
        }
    }

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        return true
    }

    override fun execute(event: Event) {
        val nametagModule = Apollo.getModuleManager().getModule(NametagModule::class.java)
        nametagModule.resetNametags(Recipients.ofEveryone())
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "reset all lunar nametags"
    }
}