package com.example.appdelivery.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appdelivery.database.dao.ShipmentDao
import com.example.appdelivery.database.entity.Shipment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Shipment::class], version = 1)
abstract class DeliveryDatabase : RoomDatabase() {

    abstract fun shipmentDao(): ShipmentDao

    private class DeliveryDatabaseCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var shipmentDao = database.shipmentDao()

                    // Delete all content here
                    shipmentDao.deleteAll()

                    var shipment = Shipment(source = "In",
                        address = "Asentamiento humano puerto nuevo Mzc1lt23 pasaje las ballestas Callao Referencia: Contramirantemora",
                        rating = 2, createdOn = "2019-06-14T00:00:00")
                    shipmentDao.insert(shipment)

                    shipment = Shipment(source = "In",
                        address = "Avenida Oscar r. Benavides 679 1512 Referencia: Lima. Entre colonial y cárcamo edificio centro colonial",
                        rating = 2, createdOn = "2019-06-14T00:00:00")
                    shipmentDao.insert(shipment)

                    shipment = Shipment(source = "In",
                        address = "Jiron libertad 860 860 401 Referencia: Espalda de Av brasil cdra 35",
                        rating = 2, createdOn = "2019-06-14T00:00:00")
                    shipmentDao.insert(shipment)

                    shipment = Shipment(source = "In",
                        address = "alle San Javier 170 urb villa marina  170  Referencia: Altura cuadra 2 de la avenida santa anita",
                        rating = 2, createdOn = "2019-06-14T00:00:00")
                    shipmentDao.insert(shipment)

                    shipment = Shipment(source = "In",
                        address = "Avenida Argentina 3093 Int L-140 (C. Comercial Minka) Referencia: TDA PASSARELA",
                        rating = 2, createdOn = "2019-06-14T00:00:00")
                    shipmentDao.insert(shipment)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DeliveryDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DeliveryDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DeliveryDatabase::class.java,
                    "delivery_database"
                )/*.addCallback(DeliveryDatabaseCallback(scope))*/.build()
                INSTANCE = instance
                return instance
            }
        }
    }
}