package com.example.employee_manager_l2_task.dataBase

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "department",indices = [Index(value = ["departmentName"], unique = true)])
data class DepartMentTable(
    @PrimaryKey(autoGenerate = true) val departmentId : Int = 0,
    val departmentName : String ,
    val description : String? = null
) {
    //below one used for dropdownitem in spinner
    override fun toString() = departmentName
}