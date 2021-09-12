package tj.ilhom.trip.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitInstance {
    @Provides
    fun providerBaseUrl() = "https://experience.tripster.ru"

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Token f5a21b7074a4ffd01da14e8bad6393eb65f42c2c")
                .build()
            chain.proceed(request)
        }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://experience.tripster.ru")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(API::class.java)

    @Provides
    @Singleton
    fun provideHelper(apiHelper: APIHelperImpl): APIHelper = apiHelper
}