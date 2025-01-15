package de.binhacken.discord.utils

import com.github.kaktushose.jda.commands.dispatching.events.ReplyableEvent
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color

inline fun embedBuilderBuilder(builder: EmbedBuilderBuilder.() -> Unit) = EmbedBuilderBuilder().apply(builder).build()
inline fun ReplyableEvent<*>.replyEmbed(builder: EmbedBuilderBuilder.() -> Unit) = reply(embedBuilderBuilder(builder))

class EmbedBuilderBuilder {

    class FieldBuilder {
        var name: String = ""
        var value: String = ""
        var inline: Boolean = false

        fun build() = MessageEmbed.Field(name, value, inline)
    }

    var title: String? = null
    var url: String? = null
    var description: String? = null
    var timestamp: Instant? = null
    var color: Color? = null
    var thumbnailUrl: String? = null
    var imageUrl: String? = null
    var author: String? = null
    var authorUrl: String? = null
    var authorIconUrl: String? = null
    var footer: String? = null
    var footerUrl: String? = null

    private val fields = mutableListOf<MessageEmbed.Field>()

    fun field(builder: FieldBuilder.() -> Unit) {
        fields += FieldBuilder().apply(builder).build()
    }

    fun build() = EmbedBuilder().apply {
        setTitle(title, url)
        setDescription(description)
        setTimestamp(timestamp?.toJavaInstant())
        setColor(color)
        setThumbnail(thumbnailUrl)
        setImage(imageUrl)
        setAuthor(author, authorUrl, authorIconUrl)
        setFooter(footer, footerUrl)
        this@EmbedBuilderBuilder.fields.forEach(this::addField)
    }
}