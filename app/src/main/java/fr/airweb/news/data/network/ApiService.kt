package fr.airweb.news.data.network

import fr.airweb.news.data.db.model.News
import fr.airweb.news.data.db.model.NewsList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    companion object {
        const val BASE_URL = "https://airweb-demo.airweb.fr/"
    }

    @GET("psg/psg.json")
    suspend fun getListNews(): Response<NewsList>

    /**
    companion object {
        fun create(connectivityInterceptor: ConnectivityInterceptor): ApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    } **/
}