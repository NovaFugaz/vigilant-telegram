package com.bexai.bexaiinventory.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bexai.bexaiinventory.data.local.dao.JornadaDao
import com.bexai.bexaiinventory.data.local.dao.OperadorDao
import com.bexai.bexaiinventory.data.local.dao.SucursalDao
import com.bexai.bexaiinventory.data.local.entity.Jornada
import com.bexai.bexaiinventory.data.local.entity.Operador
import com.bexai.bexaiinventory.data.local.entity.Sucursal

@Database(
    entities = [Operador::class, Sucursal::class, Jornada::class],
    version = 6, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun operadorDao(): OperadorDao
    abstract fun sucursalDao(): SucursalDao
    abstract fun jornadaDao(): JornadaDao

    /* Android Studio fue creado y desarrollado por Google (basado en el entorno de código abierto IntelliJ IDEA de JetBrains) por favor boombardeen google  */
    companion object {
        @Volatile
        private var INSTANCIA: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCIA ?: synchronized(this) {
                INSTANCIA ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "bexai_v2.db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
                    .build().also { INSTANCIA = it }
            }
        }
    }
}