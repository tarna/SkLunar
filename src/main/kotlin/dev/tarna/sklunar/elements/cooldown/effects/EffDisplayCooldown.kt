package dev.tarna.sklunar.elements.cooldown.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.skript.util.Timespan
import ch.njol.util.Kleenean
import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.common.icon.ItemStackIcon
import com.lunarclient.apollo.common.icon.SimpleResourceLocationIcon
import com.lunarclient.apollo.module.cooldown.Cooldown
import com.lunarclient.apollo.module.cooldown.CooldownModule
import com.lunarclient.apollo.player.ApolloPlayer
import com.lunarclient.apollo.recipients.Recipients
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack
import java.time.Duration

@Name("Display Cooldown")
@Description("Display a custom Lunar cooldown for a player")
@Examples("display custom lunar cooldown with id \"example\" using item icon diamond sword for 5 seconds to player")
@Since("0.4.0")
class EffDisplayCooldown : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffDisplayCooldown::class.java,
                "display [custom] [lunar] cooldown with id %string% using [item] icon %itemstack% for %timespan% to %apolloplayers%",
                "display [custom] [lunar] cooldown with id %string% using resource [location] %string% of size %number% for %timespan% to %apolloplayers%"
            )
        }
    }

    lateinit var id: Expression<String>
    lateinit var itemIcon: Expression<ItemStack>
    lateinit var resourceIcon: Expression<String>
    lateinit var size: Expression<Number>
    lateinit var duration: Expression<Timespan>
    lateinit var players: Expression<ApolloPlayer>

    private var usingItem = true

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        id = exprs[0] as Expression<String>
        if (matchedPattern == 0) {
            itemIcon = exprs[1] as Expression<ItemStack>
            duration = exprs[2] as Expression<Timespan>
            players = exprs[3] as Expression<ApolloPlayer>
        } else {
            resourceIcon = exprs[1] as Expression<String>
            size = exprs[2] as Expression<Number>
            duration = exprs[3] as Expression<Timespan>
            players = exprs[4] as Expression<ApolloPlayer>
            usingItem = false
        }
        return true
    }

    override fun execute(event: Event) {
        val id = id.getSingle(event) ?: return
        if (usingItem) {
            val itemIcon = itemIcon.getSingle(event) ?: return
            val duration = duration.getSingle(event) ?: return
            val players = players.getArray(event) ?: return
            if (players.isEmpty()) return

            val cooldownModule = Apollo.getModuleManager().getModule(CooldownModule::class.java)
            cooldownModule.displayCooldown(Recipients.of(players.toList()), Cooldown.builder()
                .name(id)
                .duration(Duration.ofSeconds(duration.getAs(Timespan.TimePeriod.SECOND)))
                .icon(ItemStackIcon.builder()
                    .itemName(itemIcon.type.name)
                    .build()
                )
                .build()
            )
        } else {
            val resourceIcon = resourceIcon.getSingle(event) ?: return
            val size = size.getSingle(event) ?: return
            val duration = duration.getSingle(event) ?: return
            val players = players.getArray(event) ?: return
            if (players.isEmpty()) return

            val cooldownModule = Apollo.getModuleManager().getModule(CooldownModule::class.java)
            cooldownModule.displayCooldown(Recipients.of(players.toList()), Cooldown.builder()
                .name(id)
                .duration(Duration.ofMillis(duration.getAs(Timespan.TimePeriod.MILLISECOND)))
                .icon(SimpleResourceLocationIcon.builder()
                    .resourceLocation(resourceIcon)
                    .size(size.toInt())
                    .build()
                )
                .build()
            )
        }
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "display custom lunar cooldown with id ${id.toString(event, debug)} using ${if (usingItem) "icon ${itemIcon.toString(event, debug)}" else "resource location ${resourceIcon.toString(event, debug)} of size ${size.toString(event, debug)}"} for ${duration.toString(event, debug)} to ${players.toString(event, debug)}"
    }
}