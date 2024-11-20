package dev.tarna.sklunar.api.util

import com.lunarclient.apollo.Apollo
import com.lunarclient.apollo.player.ApolloPlayer
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CommandSender.send(message: String) {
    this.sendMessage(!message)
}

fun CommandSender.send(messages: List<String>) {
    messages.forEach { this.send(it) }
}

fun CommandSender.send(vararg messages: String) {
    messages.forEach { this.send(it) }
}

fun Player.toApollo(): ApolloPlayer? {
    val apolloPlayer = Apollo.getPlayerManager().getPlayer(uniqueId)
    return if (apolloPlayer.isPresent) {
        apolloPlayer.get()
    } else {
        null
    }
}

fun List<Player>.toApollo(): List<ApolloPlayer> {
    return mapNotNull { it.toApollo() }
}

fun ApolloPlayer.toPlayer(): Player? {
    return Bukkit.getServer().getPlayer(uniqueId)
}

fun List<ApolloPlayer>.toPlayer(): List<Player> {
    return mapNotNull { it.toPlayer() }
}