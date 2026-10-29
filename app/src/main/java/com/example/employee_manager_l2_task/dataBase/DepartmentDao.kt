package com.example.employee_manager_l2_task.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {


    @Query("SELECT d.*, (SELECT COUNT(*) FROM employee e WHERE e.departmentId = d.departmentId) AS employeeCount FROM department d ORDER BY d.departmentName")
    fun observeAllWithCount() : Flow<List<DepartmentWithCount>>


    @Query("SELECT * FROM department ORDER BY departmentName")
    fun observeAll(): Flow<List<DepartMentTable>>

    @Query("SELECT * FROM department WHERE departmentId = :id")
    suspend fun getById(id: Int): DepartMentTable?

    @Insert suspend fun insert(department: DepartMentTable): Long

    @Update suspend fun update(department: DepartMentTable)

    @Delete suspend fun delete(department: DepartMentTable)
}