package com.juliane.splyza.di

import com.juliane.splyza.network.ServicesApi
import com.juliane.splyza.repository.TeamsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideTeamsRepo(servicesApi: ServicesApi): TeamsRepository {
        return TeamsRepository(servicesApi)
    }

}