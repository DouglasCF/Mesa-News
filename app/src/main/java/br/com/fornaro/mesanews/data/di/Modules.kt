package br.com.fornaro.mesanews.data.di

import android.content.Context
import androidx.room.Room
import br.com.fornaro.mesanews.BuildConfig
import br.com.fornaro.mesanews.data.dispatchers.DispatcherMap
import br.com.fornaro.mesanews.data.dispatchers.MainDispatcherMap
import br.com.fornaro.mesanews.data.repository.AuthenticationRepository
import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.data.source.local.AuthenticationLocalDataSource
import br.com.fornaro.mesanews.data.source.local.NewsLocalDataSource
import br.com.fornaro.mesanews.data.source.local.database.AppDatabase
import br.com.fornaro.mesanews.data.source.remote.AuthenticationRemoteDataSource
import br.com.fornaro.mesanews.data.source.remote.NewsRemoteDataSource
import br.com.fornaro.mesanews.data.source.remote.api.ConnectivityInterceptor
import br.com.fornaro.mesanews.data.source.remote.api.MesaApi
import br.com.fornaro.mesanews.data.source.remote.mappers.HighlightsRemoteMapper
import br.com.fornaro.mesanews.data.source.remote.mappers.NewsRemoteMapper
import br.com.fornaro.mesanews.data.source.remote.mappers.SignInRemoteMapper
import br.com.fornaro.mesanews.data.source.remote.mappers.SignUpRemoteMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val networkModules = module {
    single { providesOkHttpClient(get()) }
    single { provideMoshi() }
    single { providesRetrofit(get(), get()) }
    single { providesApi(get()) }
}

private val remoteDataSourceModules = module {
    single {
        AuthenticationRemoteDataSource(
            api = get(),
            signUpMapper = get(),
            signInMapper = get()
        )
    }

    single {

        NewsRemoteDataSource(
            api = get(),
            highlightsMapper = get(),
            newsMapper = get()
        )
    }
}

private val localDataSourceModules = module {
    single { AuthenticationLocalDataSource(context = androidContext(), userDao = get()) }

    single {
        NewsLocalDataSource(
            authenticationLocalDataSource = get(),
            newsDao = get()
        )
    }
}

private val repositoryModules = module {
    single {
        AuthenticationRepository(
            remoteDataSource = get(),
            localDataSource = get(),
            dispatcherMap = get()
        )
    }

    single {
        NewsRepository(
            authenticationRepository = get(),
            newsRemoteDataSource = get(),
            newsLocalDataSource = get(),
            dispatcherMap = get()
        )
    }
}

private val dispatcherModules = module {
    single<DispatcherMap> { MainDispatcherMap }
}

private val mapperModules = module {
    single { SignUpRemoteMapper }
    single { SignInRemoteMapper }
    single { HighlightsRemoteMapper }
    single { NewsRemoteMapper }
}

private val databaseModules = module {
    single { provideDatabase(androidContext()) }
    single { provideUserDao(get()) }
    single { provideNewsDao(get()) }
}

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "database").build()

fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()
fun provideNewsDao(appDatabase: AppDatabase) = appDatabase.newsDao()

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

val dataModules = networkModules +
        remoteDataSourceModules +
        localDataSourceModules +
        repositoryModules +
        mapperModules +
        dispatcherModules +
        databaseModules