package com.android.pagingwithflow.di

import com.android.pagingwithflow.network.ApiService
import com.android.pagingwithflow.network.NetworkingConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private val moshi  = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()!!


    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkingModule {

        @Provides
        fun providesBaseUrl(): String {
            return NetworkingConstants.BASE_URL
        }

        @Provides
        fun providesLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        @Provides
        fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            val okHttpClient = OkHttpClient().newBuilder()
                .protocols(listOf(Protocol.HTTP_1_1))
            okHttpClient.callTimeout(40, TimeUnit.SECONDS)
            okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
            okHttpClient.readTimeout(40, TimeUnit.SECONDS)
            okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
            okHttpClient.addInterceptor(loggingInterceptor)
            okHttpClient.build()
            return okHttpClient.build()
        }

        @Provides
        fun provideConverterFactory(): Converter.Factory {
            return GsonConverterFactory.create()
        }

        @Provides
        fun provideRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String, converterFactory: Converter.Factory): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .build()
        }

        @Provides
        fun provideRestApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }

}