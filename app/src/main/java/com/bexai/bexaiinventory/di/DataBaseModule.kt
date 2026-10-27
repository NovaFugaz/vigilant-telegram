package com.bexai.bexaiinventory.di

import android.content.Context
import androidx.room.Room
import com.bexai.bexaiinventory.data.local.AppDataBase
import com.bexai.bexaiinventory.data.local.dao.OperadorDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    // Acá tenemos que rellenar las cosas, una vez que tengamos listo lo del Room y to_do eso

    /*
    * Estaba viendo eso de Hilt, pero como yo no se, yo lo hago como lo ha hecho el freddy
    * pero si tu quieres editarlo hazlo
    * según yo deberia de ser asi (?)
    * si eso te ayuda, porque lo otro seria ponerle las cosas al repository y al viewmodel*/

//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): AppDataBase =
//        Room.databaseBuilder(context, AppDataBase::class.java, "bexai.db").build()
//
//    @Provides
//    fun provideOperadorDao(db: AppDataBase): OperadorDao = db.operadorDao()
    }