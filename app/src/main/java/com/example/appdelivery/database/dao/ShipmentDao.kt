package com.example.appdelivery.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appdelivery.database.entity.Shipment

@Dao
interface ShipmentDao {

    @Query("SELECT * FROM shipment ORDER BY id DESC")
    fun getAll(): LiveData<List<Shipment>>

    @Query("SELECT * FROM shipment ORDER BY id DESC LIMIT 3")
    fun getLastFive(): LiveData<List<Shipment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE) // REPLACE
    suspend fun insert(shipment: Shipment)

    @Update
    suspend fun update(shipment: Shipment)

    @Query("UPDATE shipment SET rating = :rating WHERE id = :id")
    suspend fun updateRating(id: Int, rating: Int)

    @Query("DELETE FROM shipment WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM shipment")
    suspend fun deleteAll()
}