package com.example.employee_manager_l2_task.viewmodels

import android.app.Application
import com.example.employee_manager_l2_task.dataBase.DepartMentTable

class DepartmentViewModel(app : Application) : BaseViewModel(app){

    val departments = repo.departmentWithCount.asSafeLiveData()

    fun load(id :Int , onLoaded : (DepartMentTable?) -> Unit) = launchSafely { onLoaded(repo.getDepartment(id)) }

    fun save(d : DepartMentTable ,onSuccess : () -> Unit) = launchSafely (onSuccess) {
        repo.saveDepartment(d)
    }

    fun employeeCount(id : Int , onResult : (Int) -> Unit) = launchSafely { onResult(repo.employeeCount(id)) }

    fun delete(d: DepartMentTable) = launchSafely {
        repo.deleteDepartMent(d)
        post("Department deleted")
    }

    fun deleteWithEmployees(d: DepartMentTable) = launchSafely {
        repo.deleteDepartmentWithEmployees(d)
        post("Department and its employees deleted")
    }

}