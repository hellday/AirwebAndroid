package fr.airweb.news.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ServiceApi

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScopeIO
