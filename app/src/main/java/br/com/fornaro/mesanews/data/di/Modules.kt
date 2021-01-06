package br.com.fornaro.mesanews.data.di

import android.content.Context
import br.com.fornaro.mesanews.BuildConfig
import br.com.fornaro.mesanews.data.source.remote.api.ConnectivityInterceptor
import br.com.fornaro.mesanews.data.source.remote.api.MesaApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val networkModules = module {
    single { providesOkHttpClient(get()) }
    single { provideMoshi() }
    single { providesRetrofit(get(), get()) }
    single { providesApi(get()) }
}

fun providesOkHttpClient(context: Context): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(ConnectivityInterceptor(context))
    .build()

fun provideMoshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .build()


fun providesApi(retrofit: Retrofit): MesaApi =
    retrofit.create(MesaApi::class.java)

val dataModules = networkModules