package com.galexdev.finalproject.di

import android.app.Application
import androidx.room.Room
import com.galexdev.finalproject.FakeRemoteService
import com.galexdev.finalproject.R
import com.galexdev.finalproject.data.database.MovieDao
import com.galexdev.finalproject.data.database.MovieDataBase
import com.galexdev.finalproject.data.server.RemoteService
import com.galexdev.finalproject.ui.buildRemoteMovies
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
class TestAppModule {
    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.inMemoryDatabaseBuilder(
        app,
        MovieDataBase::class.java
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDataBase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = "http://localhost:8080"

    @Provides
    @Singleton
    fun provideOkHttpClient() = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): RemoteService{

        val builder = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return builder.create()
    }

}