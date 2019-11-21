package fr.airweb.news.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.airweb.news.data.db.dao.NewsDao
import fr.airweb.news.data.db.model.News
import fr.airweb.news.data.db.model.NewsList

@Database(
    entities = [NewsList::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    //--- DAO ---///
    abstract fun newsDao(): NewsDao

    companion object{
        @Volatile private var instance: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java, "airweb.db")
                .build()
    }
}