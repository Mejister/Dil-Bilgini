package com.kelimebilmece.di


import com.kelimebilmece.remotefirebase.ApiService
import com.kelimebilmece.repository.QuestionRepository
import com.kelimebilmece.repository.QuestionRepositoryImpl
import com.kelimebilmece.room.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        firebaseDateSource: ApiService,
        roomDB: RoomDB
    ): QuestionRepository {
        return QuestionRepositoryImpl(firebaseDateSource,roomDB.DataDao())
    }
}