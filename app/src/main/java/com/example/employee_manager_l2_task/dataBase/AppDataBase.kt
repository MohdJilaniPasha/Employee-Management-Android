package com.example.employee_manager_l2_task.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [DepartMentTable::class, EmployeeTable::class, EmployeeDetailsTable::class],
    version = 2,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun departmentDao(): DepartmentDao

    abstract fun employeeDao() : EmployeeDao

    abstract fun employeeDetailsDao(): EmployeeDetailsdao


    companion object {
        @Volatile private var INSTANCE : AppDataBase? = null

        fun get(context : Context) : AppDataBase = INSTANCE ?: synchronized(this){
            INSTANCE ?:  Room.databaseBuilder(
                context.applicationContext, AppDataBase::class.java, "employee_manager_dp")
                .build().also{ INSTANCE = it }
        }
    }
}