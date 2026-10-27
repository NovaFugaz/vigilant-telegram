package com.bexai.bexaiinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bexai.bexaiinventory.data.local.entity.Operador

@Dao
interface OperadorDao {

    // no se, seria 'findById', pero no es un id, le puse codigo
    // codigo en inglés es code :p
    @Query("SELECT * FROM operadores WHERE codigo = :codigo LIMIT 1")
    suspend fun findByCode(codigo: String): Operador?

    /* Esta se usa .REPLACE, pero aun asi es solamente para los datos
    * de prueba, al momento de que se crea el viewmodel
    * pero si luego tenemos la bd se deberia de quitar
    * En si, si es la 1ra vez los operadores serian 0 (entonces quedarian registrados
    * los que deje como guardados), pero si lo vuelvo a ejecutar ya quedarian,
    * entonces los vuelve a contar y como hay operadores no los vuelve a agregar*/
    @Query("SELECT COUNT(*) FROM operadores")
    suspend fun contar(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarOperador(operador: Operador)
}