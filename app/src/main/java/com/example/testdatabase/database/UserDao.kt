package com.example.testdatabase.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM sampleuserdata")
    fun getAll(): List<UserDetails>

    @Query("SELECT * FROM sampleuserdata WHERE name LIKE '%'||:userName||'%'")
    fun searchUserDetails(userName: String): List<UserDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun getInsertData(vararg userDetails: UserDetails)

    @Delete
    fun getDeleteData(deleteUserDetails: UserDetails)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun getUpdateUserDetails(vararg updateDetails: UserDetails)
}