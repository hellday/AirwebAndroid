package fr.airweb.news.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.airweb.news.data.repository.NewsRepository

class NewsViewModelFactory(private val newsRepository: NewsRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }

}