package fr.airweb.news.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fr.airweb.news.ui.news.NewsActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeNewsActivity(): NewsActivity
}
