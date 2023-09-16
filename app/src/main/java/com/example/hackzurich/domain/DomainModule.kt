package com.example.hackzurich.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun providePostMessageUseCase(repository: IChatBotRepository): PostMessageUseCase {
        return PostMessageUseCase(repository)
    }
}