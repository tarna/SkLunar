package dev.tarna.sklunar.elements.tebex.types

import ch.njol.skript.registrations.Classes
import com.lunarclient.apollo.module.tebex.TebexEmbeddedCheckoutSupport
import dev.tarna.sklunar.api.wrapper.EnumWrapper

class Types {
    companion object {
        init {
            val tebexEmbedCheckoutSupportEum = EnumWrapper(TebexEmbeddedCheckoutSupport::class.java)
            Classes.registerClass(tebexEmbedCheckoutSupportEum.getClassInfo("tebexcheckoutsupport")
                .user("tebex (embedded|embed(ded)?) checkout support")
                .name("Tebex Embedded Checkout Support")
                .description("Represents the Tebex embedded checkout support options")
                .since("0.5.0")
            )
        }
    }
}