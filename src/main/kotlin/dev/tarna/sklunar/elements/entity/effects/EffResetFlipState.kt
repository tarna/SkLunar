package dev.tarna.sklunar.elements.entity.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.common.ApolloEntity
import com.lunarclient.apollo.module.entity.EntityModule
import com.lunarclient.apollo.recipients.Recipients
import dev.tarna.sklunar.api.util.toApollo
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Event

class EffResetFlipState : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffResetFlipState::class.java, "reset [the] [lunar] flip state of %entities% for %players%")
        }
    }

    lateinit var entities: Expression<Entity>
    lateinit var players: Expression<Player>

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        entities = exprs[0] as Expression<Entity>
        players = exprs[1] as Expression<Player>
        return true
    }

    override fun execute(event: Event) {
        val entities = entities.getArray(event) ?: return
        val players = players.getArray(event) ?: return
        if (entities.isEmpty() || players.isEmpty()) return

        val entityModule = Apollo.getModuleManager().getModule(EntityModule::class.java)
        val recipients = Recipients.of(players.toList().toApollo())
        entityModule.resetFlippedEntity(recipients, entities.map { ApolloEntity(it.entityId, it.uniqueId) })
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "reset the lunar flip state of ${entities.toString(event, debug)} for ${players.toString(event, debug)}"
    }
}