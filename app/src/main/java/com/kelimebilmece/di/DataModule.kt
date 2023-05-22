package com.test.kelimebilgini.di

import android.app.Application
import androidx.room.Room
import com.test.kelimebilgini.room.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): RoomDB {
        return Room.databaseBuilder(
            app,
            RoomDB::class.java,
            "questions.db"
        ).build()
    }
}
