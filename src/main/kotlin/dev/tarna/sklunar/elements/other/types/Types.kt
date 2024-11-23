package dev.tarna.sklunar.elements.other.types

import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.registrations.Classes
import com.lunarclient.apollo.common.ApolloEntity
import com.lunarclient.apollo.common.location.ApolloBlockLocation
import com.lunarclient.apollo.common.location.ApolloLocation
import com.lunarclient.apollo.player.ApolloPlayer
import dev.tarna.sklunar.api.util.toApollo
import dev.tarna.sklunar.api.util.toApolloBlockLocation
import dev.tarna.sklunar.api.util.toApolloLocation
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.skriptlang.skript.lang.converter.Converters

class Types {
    companion object {
        init {
            // Register ApolloPlayer
            Classes.registerClass(ClassInfo(ApolloPlayer::class.java, "apolloplayer")
                .user("apolloplayers?")
                .name("Apollo Player")
                .description("Represents an Apollo Player")
                .since("0.3.0")
            )

            Converters.registerConverter(Player::class.java, ApolloPlayer::class.java) {
                it.toApollo()
            }

            Converters.registerConverter(ApolloPlayer::class.java, Player::class.java) {
                it.player as Player
            }

            Converters.registerConverter(ApolloPlayer::class.java, String::class.java) {
                (it.player as Player).name
            }

            // Register ApolloLocation
            Classes.registerClass(ClassInfo(ApolloLocation::class.java, "apollolocation")
                .user("apollolocations?")
                .name("Apollo Location")
                .description("Represents an Apollo Location")
                .since("0.3.0")
            )

            Converters.registerConverter(Location::class.java, ApolloLocation::class.java) {
                it.toApolloLocation()
            }

            Converters.registerConverter(ApolloLocation::class.java, Location::class.java) {
                Location(Bukkit.getWorld(it.world), it.x, it.y, it.z)
            }

            // Register ApolloBlockLocation
            Classes.registerClass(ClassInfo(ApolloBlockLocation::class.java, "apolloblocklocation")
                .user("apolloblocklocations?")
                .name("Apollo Block Location")
                .description("Represents an Apollo Block Location")
                .since("0.3.0")
            )

            Converters.registerConverter(Location::class.java, ApolloBlockLocation::class.java) {
                it.toApolloBlockLocation()
            }

            Converters.registerConverter(ApolloBlockLocation::class.java, Location::class.java) {
                Location(Bukkit.getWorld(it.world), it.x.toDouble(), it.y.toDouble(), it.z.toDouble())
            }

            // Register ApolloEntity
            Classes.registerClass(ClassInfo(ApolloEntity::class.java, "apolloentity")
                .user("apollo ?entit(y|ies)")
                .name("Apollo Entity")
                .description("Represents an Apollo Entity")
                .since("0.3.0")
            )

            Converters.registerConverter(Entity::class.java, ApolloEntity::class.java) {
                ApolloEntity(it.entityId, it.uniqueId)
            }

            Converters.registerConverter(ApolloEntity::class.java, Entity::class.java) {
                Bukkit.getEntity(it.entityUuid)
            }
        }
    }
}