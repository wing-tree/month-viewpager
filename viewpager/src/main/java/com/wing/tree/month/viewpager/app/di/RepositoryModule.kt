package com.wing.tree.month.viewpager.app.di

import android.content.Context
import com.wing.tree.month.viewpager.domain.repository.instance.InstancesRepository
import com.wing.tree.month.viewpager.data.repository.instance.InstancesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object RepositoryModule {
    @Provides
    @Singleton
    fun providesInstanceRepository(@ApplicationContext context: Context): InstancesRepository {
        return InstancesRepositoryImpl(context)
    }
}