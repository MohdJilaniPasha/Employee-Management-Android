package com.example.employee_manager_l2_task.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDetailsdao {

    @Query("SELECT d.detailsId, d.employeeId, e.employeeName, dep.departmentName, d.designation, d.salary, d.joiningDate FROM employee_details d INNER JOIN employee e ON d.employeeId = e.employeeId INNER JOIN department dep ON e.departmentId = dep.departmentId ORDER BY e.employeeName")
    fun observeAll(): Flow<List<EmployeeDetailsView>>

    @Query("SELECT d.detailsId, d.employeeId, e.employeeName, dep.departmentName, d.designation, d.salary, d.joiningDate FROM employee_details d INNER JOIN employee e ON d.employeeId = e.employeeId INNER JOIN department dep ON e.departmentId = dep.departmentId WHERE d.detailsId = :id")
    suspend fun getViewById(id: Int): EmployeeDetailsView?

    @Query("DELETE FROM employee_details WHERE detailsId = :id")
    suspend fun deleteById(id: Int)

    @Insert suspend fun insert(details: EmployeeDetailsTable): Long
    @Update suspend fun update(details: EmployeeDetailsTable)
}