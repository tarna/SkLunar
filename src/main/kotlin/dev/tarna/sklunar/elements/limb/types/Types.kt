package dev.tarna.sklunar.elements.limb.types

import ch.njol.skript.registrations.Classes
import com.lunarclient.apollo.module.limb.ArmorPiece
import com.lunarclient.apollo.module.limb.BodyPart
import dev.tarna.sklunar.api.wrapper.EnumWrapper

class Types {
    companion object {
        init {
            val armorPieceEnum = EnumWrapper(ArmorPiece::class.java, "armor", null)
            Classes.registerClass(armorPieceEnum.getClassInfo("armorpiece")
                .user("armor ?pieces?")
                .name("Armor pieces")
                .description("Represents the armor pieces")
                .since("0.4.0")
            )

            val bodyPartEnum = EnumWrapper(BodyPart::class.java, "body", null)
            Classes.registerClass(bodyPartEnum.getClassInfo("bodypart")
                .user("body ?parts?")
                .name("Body parts")
                .description("Represents the body parts")
                .since("0.4.0")
            )
        }
    }
}