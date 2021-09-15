package tj.ilhom.trip.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RestClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RestClientV2

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Retrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitV2