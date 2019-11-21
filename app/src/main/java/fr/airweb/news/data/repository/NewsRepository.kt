package fr.airweb.news.data.repository

import fr.airweb.news.data.db.dao.NewsDao
import fr.airweb.news.data.network.NewsDataSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val dataSource: NewsDataSource) {

    val news = resultLiveData(
        databaseQuery = { newsDao.getAllNews() },
        networkCall = { dataSource.fetchData() },
        saveCallResult = { newsDao.insertAll(it) }
    )
}