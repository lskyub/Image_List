package com.sklim.domain.di

import com.sklim.domain.repository.ImagesRepository
import com.sklim.domain.usecase.ImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideImagesUseCase(repository: ImagesRepository): ImagesUseCase {
        return ImagesUseCase(repository)
    }
}