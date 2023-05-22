package com.test.kelimebilgini.di


import com.test.kelimebilgini.remotefirebase.ApiService
import com.test.kelimebilgini.repository.QuestionRepository
import com.test.kelimebilgini.repository.QuestionRepositoryImpl
import com.test.kelimebilgini.room.RoomDB
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