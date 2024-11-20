package dev.tarna.sklunar.api.util

import ch.njol.skript.Skript
import ch.njol.skript.classes.Parser
import ch.njol.skript.command.EffectCommandEvent
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.ExpressionInfo
import ch.njol.skript.lang.ParseContext
import ch.njol.skript.lang.TriggerItem
import ch.njol.skript.lang.parser.ParserInstance
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.inventory.EquipmentSlotGroup
import java.lang.reflect.Field
import java.util.*
import java.util.concurrent.atomic.AtomicInteger


/**
 * Utility class to handle Skript things
 * @see [SkriptUtils](https://github.com/ShaneBeee/SkBee/blob/master/src/main/java/com/shanebeestudios/skbee/api/util/SkriptUtils.java)
 */
object SkriptUtils {
    private var LAST_SPAWNED: Field? = null

    init {
        val effSpawnClass: Class<*>
        try {
            effSpawnClass = if (Skript.classExists("ch.njol.skript.sections.EffSecSpawn")) {
                Class.forName("ch.njol.skript.sections.EffSecSpawn")
            } else {
                Class.forName("ch.njol.skript.effects.EffSpawn")
            }
            LAST_SPAWNED = effSpawnClass.getDeclaredField("lastSpawned")
        } catch (ignore: ClassNotFoundException) {
        } catch (ignore: NoSuchFieldException) {
        }
    }

    /**
     * Set last spawned entity
     *
     * Skript changed the name of the EffSpawn class so now we gotta use this method
     *
     * @param entity Entity that was spawned last
     */
    fun setLastSpawned(entity: Entity?) {
        if (LAST_SPAWNED != null) {
            try {
                LAST_SPAWNED!!.set(null, entity)
            } catch (ignore: IllegalAccessException) {
            }
        }
    }

    /**
     * Get counts of loaded Skript elements
     * <br></br>
     * In order events, effects, expressions, conditions, sections
     *
     * @return Counts of loaded Skript elements
     */
    fun getElementCount(): IntArray {
        val i = IntArray(5)

        i[0] = Skript.getEvents().size
        i[1] = Skript.getEffects().size
        val exprs = AtomicInteger()
        Skript.getExpressions().forEachRemaining { e: ExpressionInfo<*, *>? -> exprs.getAndIncrement() }
        i[2] = exprs.get()
        i[3] = Skript.getConditions().size
        i[4] = Skript.getSections().size

        return i
    }

    /**
     * Parse a string as an effect
     *
     * @param stringEffect String to parse
     * @param sender       Who it was sent from
     * @return True if parsed correctly else false
     */
    fun parseEffect(stringEffect: String?, sender: CommandSender?): Boolean {
        val parserInstance = ParserInstance.get()
        parserInstance.setCurrentEvent("effect command", EffectCommandEvent::class.java)
        val effect = Effect.parse(stringEffect, null)
        parserInstance.deleteCurrentEvent()
        return if (effect != null) {
            TriggerItem.walk(effect, EffectCommandEvent(sender, stringEffect))
        } else {
            false
        }
    }

    /** Get a default instance of a Parser for ClassInfos
     * @param <T> ClassType
     * @return New instance of default parser
    </T> */
    fun <T> getDefaultParser(): Parser<T> {
        return object : Parser<T>() {
            override fun canParse(context: ParseContext?): Boolean {
                return false
            }

            override fun toString(o: T, flags: Int): String {
                return o.toString()
            }

            override fun toVariableNameString(o: T): String {
                return o.toString()
            }
        }
    }

    fun equipmentSlotGroups(): Map<String, EquipmentSlotGroup> {
        val groups: MutableMap<String, EquipmentSlotGroup> = HashMap()
        for (declaredField in EquipmentSlotGroup::class.java.declaredFields) {
            if (EquipmentSlotGroup::class.java.isAssignableFrom(declaredField.type)) {
                try {
                    val equipmentSlotGroup = declaredField[null] as EquipmentSlotGroup
                    val name = declaredField.name.lowercase(Locale.ROOT) + "_slot_group"
                    groups[name] = equipmentSlotGroup
                } catch (e: IllegalAccessException) {
                    throw RuntimeException(e)
                }
            }
        }
        return groups
    }
}