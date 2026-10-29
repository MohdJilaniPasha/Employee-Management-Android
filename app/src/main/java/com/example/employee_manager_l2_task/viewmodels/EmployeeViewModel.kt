package com.example.employee_manager_l2_task.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.employee_manager_l2_task.dataBase.EmployeeTable

class EmployeeViewModel(app: Application) : BaseViewModel(app) {
    private val query = MutableLiveData("")

    val employees = query.switchMap { q -> repo.searchEmployees(q.trim()).asSafeLiveData() }

    val departments = repo.departments.asSafeLiveData() // for the dropdown

    fun search(text: String) { if (query.value != text) query.value = text }

    fun load(id: Int, onLoaded: (EmployeeTable?) -> Unit) = launchSafely { onLoaded(repo.getEmployee(id)) }

    fun save(e: EmployeeTable, onSuccess: () -> Unit) = launchSafely(onSuccess) {
        repo.saveEmployee(e)
    }

    fun delete(e: EmployeeTable) = launchSafely {
        repo.deleteEmployee(e)
        post("Employee deleted")
    }
}