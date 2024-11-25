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
import com.lunarclient.apollo.module.limb.BodyPart
import org.bukkit.event.Event

@Name("All Body Part")
@Description("Get all body parts")
@Examples("send \"There are %size of all body parts% body parts!\"")
@Since("0.4.0")
class ExprAllBodyPart : SimpleExpression<BodyPart>() {
    companion object {
        init {
            Skript.registerExpression(ExprAllBodyPart::class.java, BodyPart::class.java, ExpressionType.SIMPLE, "all [of] [the] body parts")
        }
    }

    override fun init(exprs: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: ParseResult): Boolean {
        return true
    }

    override fun get(event: Event): Array<BodyPart> {
        return BodyPart.entries.toTypedArray()
    }

    override fun isSingle(): Boolean {
        return false
    }

    override fun getReturnType(): Class<out BodyPart> {
        return BodyPart::class.java
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "all body parts"
    }
}