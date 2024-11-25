package dev.tarna.sklunar.elements.limb.expressions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser.ParseResult
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.lunarclient.apollo.module.limb.ArmorPiece
import org.bukkit.event.Event

@Name("All Armor Piece")
@Description("Get all armor pieces")
@Examples("send \"There are %size of all armor pieces% armor pieces!\"")
@Since("0.4.0")
class ExprAllArmorPiece : SimpleExpression<ArmorPiece>() {
    companion object {
        init {
            Skript.registerExpression(ExprAllArmorPiece::class.java, ArmorPiece::class.java, ExpressionType.SIMPLE, "all [of] [the] armor pieces")
        }
    }

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        return true
    }

    override fun get(event: Event): Array<ArmorPiece> {
        return ArmorPiece.entries.toTypedArray()
    }

    override fun isSingle(): Boolean {
        return false
    }

    override fun getReturnType(): Class<out ArmorPiece> {
        return ArmorPiece::class.java
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "all armor pieces"
    }
}