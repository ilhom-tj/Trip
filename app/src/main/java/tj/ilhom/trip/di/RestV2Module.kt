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
import tj.ilhom.trip.network.repo.BackendRepo
import tj.ilhom.trip.network.repo.BackendRepoImpl
import tj.ilhom.trip.network.repo.Repo
import tj.ilhom.trip.network.repo.RepoImpl
import tj.ilhom.trip.network.rest.RestServiceV2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModuleV2 {

    @Singleton
    @Provides
    @RestClientV2
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

    @Singleton
    @Provides
    @RetrofitV2
    fun provideRetrofit(@RestClientV2 okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://backendapi.site/api/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(@RetrofitV2 retrofit: Retrofit): RestServiceV2 =
        retrofit.create(RestServiceV2::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
interface BindsModuleV2 {
    @Binds
    @Singleton
    fun bindBackendRepo(r: BackendRepoImpl): BackendRepo
}