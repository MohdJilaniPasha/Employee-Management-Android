package com.example.employee_manager_l2_task.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Transaction
    @Query("SELECT * FROM employee WHERE employeeName LIKE '%' || :query || '%' ORDER BY employeeName")
    fun search(query: String): Flow<List<EmployeeWithDepartment>>

    @Query("SELECT * FROM employee WHERE employeeId NOT IN (SELECT employeeId FROM employee_details) ORDER BY employeeName")
    fun observeWithoutDetails(): Flow<List<EmployeeTable>>

    @Query("SELECT * FROM employee WHERE employeeId = :id")
    suspend fun getById(id: Int): EmployeeTable?

    @Query("SELECT COUNT(*) FROM employee WHERE departmentId = :departmentId")
    suspend fun countByDepartment(departmentId: Int): Int

    @Query("DELETE FROM employee WHERE departmentId = :departmentId")
    suspend fun deleteByDepartment(departmentId: Int)

    @Insert suspend fun insert(employee: EmployeeTable): Long
    @Update suspend fun update(employee: EmployeeTable)
    @Delete suspend fun delete(employee: EmployeeTable)
}