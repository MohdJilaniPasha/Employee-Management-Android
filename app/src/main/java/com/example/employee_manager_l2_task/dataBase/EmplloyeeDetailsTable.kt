package com.example.employee_manager_l2_task.dataBase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "employee_details",
    foreignKeys = [ForeignKey(
        entity = EmployeeTable::class,
        parentColumns = ["employeeId"],
        childColumns = ["employeeId"],
        onDelete = ForeignKey.CASCADE
    )],
     indices = [Index(value = ["employeeId"], unique = true)]
)
data class EmployeeDetailsTable(
    @PrimaryKey(autoGenerate = true) val detailsId: Int = 0,
    val employeeId: Int,
    val designation: String,
    val salary: Double,
    val joiningDate: Long
)