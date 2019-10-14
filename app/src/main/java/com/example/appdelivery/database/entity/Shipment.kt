package com.example.appdelivery.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "shipment")
data class Shipment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @SerializedName("source")
    @ColumnInfo(name = "source")
    val source: String,

    @SerializedName("address")
    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "rating")
    val rating: Int = 0,

    @SerializedName("createdon")
    @ColumnInfo(name = "createdOn")
    val createdOn: String
)