package com.example.employee_manager_l2_task.viewmodels

import android.app.Application
import com.example.employee_manager_l2_task.dataBase.EmployeeDetailsTable
import com.example.employee_manager_l2_task.dataBase.EmployeeDetailsView


class DeatailsViewModel(app : Application) : BaseViewModel(app){
    val detailsList= repo.detailsList.asSafeLiveData()

    val employeesWithoutDetails = repo.employeesWithoutDetails.asSafeLiveData()

    fun load(id: Int, onLoaded: (EmployeeDetailsView?) -> Unit) = launchSafely { onLoaded(repo.getDetails(id)) }

    fun save(d: EmployeeDetailsTable, onSuccess: () -> Unit) = launchSafely(onSuccess) {
        repo.saveDetails(d)
    }

    fun delete(id: Int) = launchSafely {
        repo.deleteDetails(id)
        post("Details deleted")
    }
}