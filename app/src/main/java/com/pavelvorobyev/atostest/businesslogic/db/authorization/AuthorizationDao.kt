package com.pavelvorobyev.atostest.businesslogic.db.authorization

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pavelvorobyev.atostest.businesslogic.pojo.Authorization
import io.reactivex.Flowable

@Dao
interface AuthorizationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authorization: Authorization)

    @Query("SELECT * FROM authorization_table")
    fun getAuthorizations(): List<Authorization>

    @Query("SELECT * FROM authorization_table")
    fun getAuthorizationsFlowable(): Flowable<List<Authorization>>

}