package com.bexai.bexaiinventory.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bexai.bexaiinventory.data.local.dao.OperadorDao
import com.bexai.bexaiinventory.data.local.entity.Operador

@Database(entities = [Operador::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun operadorDao(): OperadorDao

    companion object {
        @Volatile
        private var INSTANCIA: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase{
            return INSTANCIA ?: synchronized(this){
                INSTANCIA ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "bexai.db").build().also { INSTANCIA = it }

            }
        }
    }
}