package fr.airweb.news.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import fr.airweb.news.data.db.NewsDatabase
import fr.airweb.news.data.network.ApiService
import fr.airweb.news.data.network.NewsDataSource
import fr.airweb.news.data.network.connectivity.AuthInterceptor
import fr.airweb.news.di.CoroutineScopeIO
import fr.airweb.news.di.ServiceApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {


    @Singleton
    @Provides
    fun provideApiService(@ServiceApi okhttpClient: OkHttpClient,
                          converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, ApiService::class.java)

    @Singleton
    @Provides
    fun provideNewsDataSource(service: ApiService)
            = NewsDataSource(service)

    @ServiceApi
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .addInterceptor(AuthInterceptor("")).build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = NewsDatabase.getInstance(app)


    @Singleton
    @Provides
    fun provideNewsDao(db: NewsDatabase) = db.newsDao()

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}
