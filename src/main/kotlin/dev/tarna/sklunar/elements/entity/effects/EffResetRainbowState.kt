package dev.tarna.sklunar.elements.entity.effects

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
import com.lunarclient.apollo.common.ApolloEntity
import com.lunarclient.apollo.module.entity.EntityModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.entity.Sheep
import org.bukkit.event.Event

@Name("Reset Rainbow State")
@Description("Reset the lunar rainbow state of an entity for a player")
@Examples("reset the lunar rainbow state of target entity for player")
@Since("0.2.0")
class EffResetRainbowState : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffResetRainbowState::class.java, "reset [the] [lunar] rainbow state of %apolloentities% for %apolloplayers%")
        }
    }

    lateinit var entities: Expression<ApolloEntity>
    lateinit var players: Expression<ApolloPlayer>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        entities = exprs[0] as Expression<ApolloEntity>
        players = exprs[1] as Expression<ApolloPlayer>
        return true
    }

    override fun execute(event: Event) {
        val entities = entities.getArray(event) ?: return
        val players = players.getArray(event) ?: return
        if (entities.isEmpty() || players.isEmpty()) return

        val entityModule = Apollo.getModuleManager().getModule(EntityModule::class.java)
        val recipients = Recipients.of(players.toList())
        val sheep = entities.filterIsInstance<Sheep>()
            .map { ApolloEntity(it.entityId, it.uniqueId) }
        entityModule.resetRainbowSheep(recipients, sheep)
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "reset the lunar rainbow state of ${entities.toString(event, debug)} for ${players.toString(event, debug)}"
    }
}