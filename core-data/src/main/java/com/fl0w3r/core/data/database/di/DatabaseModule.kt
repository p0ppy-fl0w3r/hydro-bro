package com.fl0w3r.core.data.database.di

import android.content.Context
import androidx.room.Room
import com.fl0w3r.core.data.database.HydroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): HydroDatabase {
        val builder = Room.databaseBuilder(
            context, HydroDatabase::class.java, "hydro.db",
        ).createFromAsset("database/data.db")

        return builder.build()
    }
}