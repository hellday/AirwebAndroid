package fr.airweb.news.data.db

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import java.util.Collections.emptyList
import com.google.gson.Gson
import fr.airweb.news.data.db.model.News
import java.util.*


class ConverterRoom {
    var gson = Gson()

    @TypeConverter
    fun stringToNewsList(data: String?): List<News> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<News>>() {

        }.type

        return gson.fromJson<List<News>>(data, listType)
    }

    @TypeConverter
    fun newsListToString(someObjects: List<News>): String {
        return gson.toJson(someObjects)
    }
}