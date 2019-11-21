package fr.airweb.news.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import fr.airweb.news.data.db.model.News
import fr.airweb.news.data.db.model.NewsList
import android.provider.MediaStore.Video
import java.lang.UnsupportedOperationException


class ConverterJson {

    fun fromJson(json: NewsList) : List<News> {
        return json.news
    }

    fun toJson(json: List<News>): NewsList {
        throw UnsupportedOperationException()
    }
}