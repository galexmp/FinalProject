package com.galexdev.finalproject.di

import android.app.Application
import androidx.room.Room
import com.galexdev.finalproject.R
import com.galexdev.finalproject.data.AndroidPermissionChecker
import com.galexdev.finalproject.data.PermissionChecker
import com.galexdev.finalproject.data.PlayServicesLocationDataSource
import com.galexdev.finalproject.data.database.MovieDataBase
import com.galexdev.finalproject.data.database.MovieRoomDataSource
import com.galexdev.finalproject.data.datasource.LocationDataSource
import com.galexdev.finalproject.data.datasource.MovieLocalDataSource
import com.galexdev.finalproject.data.datasource.MovieRemoteDataSource
import com.galexdev.finalproject.data.server.MovieServerDataSource
import com.galexdev.finalproject.data.server.RemoteService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(app: Application) : String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun provideDatabase(app:Application) = Room.databaseBuilder(
        app,
        MovieDataBase::class.java,
        "movie-db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: MovieDataBase) = db.movieDao()

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String): RemoteService{
        val okHttpClient = HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().addInterceptor(this).build()
        }

        val builder = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return builder.create()
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule{
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: MovieServerDataSource): MovieRemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSource: MovieRoomDataSource): MovieLocalDataSource

    @Binds
    abstract fun bindLocationDataSource(playServicesLocationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}