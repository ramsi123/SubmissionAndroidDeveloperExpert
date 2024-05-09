package com.example.core.di

import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.GitHubRepository
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.room.GitHubDatabase
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.retrofit.ApiService
import com.example.core.domain.repository.IGitHubRepository
import com.example.core.util.Constants
import com.example.core.util.SettingPreferences
import com.google.gson.GsonBuilder
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<GitHubDatabase>().gitHubDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("githubapp".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            GitHubDatabase::class.java, "News.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
                .build()
            chain.proceed(requestHeaders)
        }
        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/lmo8/KPXoMsxI+J9hY+ibNm2r0IYChmOsF9BxD74PVc=")
            .add(hostname, "sha256/6YBE8kK4d5J1qu1wEjyoKqzEIvyRY5HyM/NB2wKdcZo=")
            .add(hostname, "sha256/ICGRfpgmOUXIWcQ/HXPLQTkFPEFPoDyjvH7ohhQpjzs=")
            .build()
        val client = OkHttpClient.Builder().also {
            it.addInterceptor(loggingInterceptor)
            it.addInterceptor(authInterceptor)
            it.certificatePinner(certificatePinner)
        }.build()

        client
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { SettingPreferences(get()) }
    single<IGitHubRepository> {
        GitHubRepository(get(), get(), get())
    }
}