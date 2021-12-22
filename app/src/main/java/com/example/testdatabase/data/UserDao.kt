package com.example.testdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM sampleuserdata")
    fun getAll(): List<UserDetails>

    @Query("SELECT * FROM sampleuserdata ORDER BY experience ASC")
    fun getAllData(): LiveData<List<UserDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun getInsertData(vararg userDetails: UserDetails)

    @Delete
    fun getDeleteData(deleteUserDetails: UserDetails)

    @Update
    fun getUpdateUserDetails(updateDetails:UserDetails)
}