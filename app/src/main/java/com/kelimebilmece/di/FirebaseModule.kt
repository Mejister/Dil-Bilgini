package com.kelimebilmece.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
    @Module
    object FirebaseModule {
        @Provides
        @Singleton
        fun provideFirebaseService(): FirebaseAuth = FirebaseAuth.getInstance()

        @Provides
        @Singleton
        fun provideFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()
    }

