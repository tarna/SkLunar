package dev.tarna.sklunar.api.wrapper

import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.classes.Parser
import ch.njol.skript.lang.ParseContext
import ch.njol.util.StringUtils
import org.jetbrains.annotations.NotNull
import org.skriptlang.skript.lang.comparator.Comparators
import org.skriptlang.skript.lang.comparator.Relation
import java.util.*
import javax.annotation.Nullable
import kotlin.collections.HashMap

/**
 * Wrapper class for wrapping Enums to be used in Skript
 * <p>
 * This class is copied from Skript, with the language node stripped out and other changes
 * <a href="https://github.com/SkriptLang/Skript/blob/master/src/main/java/ch/njol/skript/util/EnumUtils.java">EnumUtils</a>
 * </p>
 *
 * @author ShaneBeee (modified)
 * @author Peter Güttinger (original)
 */
class EnumWrapper<E : Enum<E>>(
    @NotNull private val enumClass: Class<E>,
    @Nullable prefix: String? = null,
    @Nullable suffix: String? = null
) {

    private val names: Array<String>
    private val parseMap = HashMap<String, E>()

    init {
        require(enumClass.isEnum) { "Class must be an enum" }
        names = Array(enumClass.enumConstants.size) { "" }

        for (enumConstant in enumClass.enumConstants) {
            var name = enumConstant.name.lowercase(Locale.getDefault())
            if (prefix != null && !name.startsWith(prefix)) name = "${prefix}_$name"
            if (suffix != null && !name.endsWith(suffix)) name = "${name}_$suffix"
            parseMap[name] = enumConstant
            names[enumConstant.ordinal] = name
        }
        registerComparator(enumClass)
    }

    @Nullable
    fun parse(s: String): E? {
        return parseMap[s.lowercase(Locale.getDefault()).replace(" ", "_")]
    }

    /**
     * Replace a specific key with another
     * Useful to prevent conflicts
     *
     * @param toReplace   Key to replace
     * @param replacement New replacement key
     */
    fun replace(toReplace: String, replacement: String) {
        if (parseMap.containsKey(toReplace)) {
            val e = parseMap[toReplace]
            val newReplacement = replacement.replace(" ", "_")
            parseMap[newReplacement] = e!!
            parseMap.remove(toReplace)
            names[e.ordinal] = newReplacement
        }
    }

    fun toString(e: E, flags: Int): String {
        return names[e.ordinal]
    }

    private fun getAllNames(): String {
        val namesList = names.toList()
        return StringUtils.join(namesList.sorted(), ", ")
    }

    inner class EnumParser : Parser<E>() {

        @Nullable
        override fun parse(s: String, context: ParseContext): E? {
            return this@EnumWrapper.parse(s)
        }

        @NotNull
        override fun toString(o: E, flags: Int): String {
            return this@EnumWrapper.toString(o, flags)
        }

        @NotNull
        override fun toVariableNameString(o: E): String {
            return toString(o, 0)
        }
    }

    /**
     * Create ClassInfo with default parser and usage
     *
     * @param codeName Name for class info
     * @return ClassInfo with default parser and usage
     */
    fun getClassInfo(codeName: String): ClassInfo<E> {
        return ClassInfo(enumClass, codeName).usage(getAllNames()).parser(EnumParser())
    }

    /**
     * Create ClassInfo with default parser and usage
     * If using `.usage()` use this method to prevent double call/assertion error
     *
     * @param codeName Name for class info
     * @return ClassInfo with default parser and usage
     */
    fun getClassInfoWithoutUsage(codeName: String): ClassInfo<E> {
        return ClassInfo(enumClass, codeName).parser(EnumParser())
    }

    private fun registerComparator(c: Class<E>) {
        Comparators.registerComparator(c, c) { o1, o2 -> Relation.get(o1 == o2) }
    }
}