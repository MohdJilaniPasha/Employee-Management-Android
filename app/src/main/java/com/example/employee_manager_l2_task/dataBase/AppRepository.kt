package com.example.employee_manager_l2_task.dataBase

import androidx.room.withTransaction

class AppRepository(private val db : AppDataBase) {

    private val departmentDao = db.departmentDao()
    private val employeeDao = db.employeeDao()

    private val detailsDao = db.employeeDetailsDao()


    // department related functions

    val departmentWithCount = departmentDao.observeAllWithCount()

    val departments = departmentDao.observeAll()

    suspend fun getDepartment(id :Int) = departmentDao.getById(id)

    suspend fun saveDepartment(d : DepartMentTable){
        if(d.departmentId == 0) departmentDao.insert(d) else departmentDao.update(d)
    }

    suspend fun employeeCount(departmentId: Int) = employeeDao.countByDepartment(departmentId)

    suspend fun deleteDepartMent(d : DepartMentTable) = departmentDao.delete(d)

    suspend fun deleteDepartmentWithEmployees(d: DepartMentTable) = db.withTransaction {
        employeeDao.deleteByDepartment(d.departmentId)
        departmentDao.delete(d)
    }


    // Employee related functions
    fun searchEmployees(query: String) = employeeDao.search(query)
    val employeesWithoutDetails = employeeDao.observeWithoutDetails()
    suspend fun getEmployee(id: Int) = employeeDao.getById(id)
    suspend fun saveEmployee(e: EmployeeTable) {
        if (e.employeeId == 0) employeeDao.insert(e) else employeeDao.update(e)
    }
    suspend fun deleteEmployee(e: EmployeeTable) = employeeDao.delete(e)


    // Emplayee details related functions
    val detailsList = detailsDao.observeAll()

    suspend fun getDetails(id: Int) = detailsDao.getViewById(id)

    suspend fun saveDetails(d: EmployeeDetailsTable) {
        if (d.detailsId == 0) detailsDao.insert(d) else detailsDao.update(d)
    }
    suspend fun deleteDetails(id: Int) = detailsDao.deleteById(id)


}