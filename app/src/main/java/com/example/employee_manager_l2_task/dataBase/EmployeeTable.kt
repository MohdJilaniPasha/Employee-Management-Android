package com.example.employee_manager_l2_task.dataBase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "employee",
    foreignKeys = [ForeignKey(
        entity = DepartMentTable::class,
        parentColumns = ["departmentId"],
        childColumns = ["departmentId"],
        onDelete = ForeignKey.RESTRICT
    )],
    indices = [Index("departmentId")]
)
data class EmployeeTable(
    @PrimaryKey(autoGenerate = true) val employeeId : Int = 0 ,
    val employeeName : String,
    val email : String ,
    val phoneNumber : String? = null ,
    val departmentId : Int
){
    override fun toString() = employeeName
}