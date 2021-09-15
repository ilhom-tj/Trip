package tj.ilhom.trip.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tj.ilhom.trip.network.repo.Repo
import tj.ilhom.trip.network.repo.RepoImpl
import tj.ilhom.trip.network.rest.RestService
import javax.inject.Singleton
import tj.ilhom.trip.di.Retrofit as DiRetrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    @RestClient
    fun provideOkHttpClient() =
        OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Token f5a21b7074a4ffd01da14e8bad6393eb65f42c2c")
                .build()
            chain.proceed(request)
        }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Singleton
    @Provides
    @DiRetrofit
    fun provideRetrofit(@RestClient okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://experience.tripster.ru")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(@DiRetrofit retrofit: Retrofit): RestService =
        retrofit.create(RestService::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {
    @Binds
    @Singleton
    fun provideRepo(repoImpl: RepoImpl): Repo
}