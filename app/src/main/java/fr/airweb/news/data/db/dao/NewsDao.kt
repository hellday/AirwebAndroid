package fr.airweb.news.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.airweb.news.data.db.model.News
import fr.airweb.news.data.db.model.NewsList

@Dao
interface NewsDao {

    @Insert
    fun insert(news: NewsList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: NewsList)

    @Update
    fun update(news: NewsList)

    @Delete
    fun delete(news: NewsList)

    @Query("DELETE FROM news_table")
    fun deleteAllNews()

    @Query("SELECT * FROM news_table")
    fun getAllNews(): LiveData<NewsList>

    /**
    @Query("SELECT * FROM  news_table WHERE type = :type")
    fun getNewsByType(type: String) : NewsList
    **/
}