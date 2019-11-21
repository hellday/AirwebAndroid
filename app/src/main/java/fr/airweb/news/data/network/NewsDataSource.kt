package fr.airweb.news.data.network

import javax.inject.Inject

/**
 * Works with the API to get data.
 */

class NewsDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {
    suspend fun fetchData() = getResult { apiService.getListNews()}
}