package fr.airweb.news.ui.news

import androidx.lifecycle.ViewModel
import fr.airweb.news.data.repository.NewsRepository
import javax.inject.Inject

/**
 * The ViewModel for News.
 */
class NewsViewModel @Inject constructor(newsRepository: NewsRepository)
: ViewModel() {

    val news = newsRepository.news

}