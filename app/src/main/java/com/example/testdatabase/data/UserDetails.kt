package com.example.testdatabase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SampleUserData")
data class UserDetails(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    var experience: Int,
    var name: String,
    var degree: String
)