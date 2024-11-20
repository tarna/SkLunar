package dev.tarna.sklunar.api.util

import com.lunarclient.apollo.common.location.ApolloBlockLocation
import com.lunarclient.apollo.common.location.ApolloLocation
import org.bukkit.Location

fun Location.toApolloLocation(): ApolloLocation {
    return ApolloLocation.builder()
        .world(world.name)
        .x(x)
        .y(y)
        .z(z)
        .build()
}

fun Location.toApolloBlockLocation(): ApolloBlockLocation {
    return ApolloBlockLocation.builder()
        .world(world.name)
        .x(x.toInt())
        .y(y.toInt())
        .z(z.toInt())
        .build()
}