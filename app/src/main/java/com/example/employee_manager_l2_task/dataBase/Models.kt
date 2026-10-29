package com.example.employee_manager_l2_task.dataBase

import androidx.room.Embedded
import androidx.room.Relation


data class DepartmentWithCount (
    @Embedded val departMentTable: DepartMentTable,
    val employeeCount : Int
)

data class EmployeeWithDepartment(
    @Embedded val employee: EmployeeTable,
    @Relation(parentColumn = "departmentId", entityColumn = "departmentId")
    val department: DepartMentTable
)

data class EmployeeDetailsView(
    val detailsId: Int,
    val employeeId: Int,
    val employeeName: String,
    val departmentName: String,
    val designation: String,
    val salary: Double,
    val joiningDate: Long
)
