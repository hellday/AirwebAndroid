package fr.airweb.news.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import fr.airweb.news.data.db.ConverterRoom

@Entity(tableName = "news_table")
@TypeConverters(ConverterRoom::class)
data class NewsList(
    @PrimaryKey
    val id: Int,
    val news: List<News>
)

data class News(
    @PrimaryKey
    @SerializedName(value = "id")
    val nid: Int,

    @SerializedName(value = "type")
    val type: String,

    @SerializedName(value = "date")
    val date: String,

    @SerializedName(value = "title")
    val title: String,

    @SerializedName(value = "picture")
    val picture: String,

    @SerializedName(value = "content")
    val content: String,

    @SerializedName(value = "dateformated")
    val dateformated: String
)