package com.example.employee_manager_l2_task.viewmodels

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.employee_manager_l2_task.dataBase.AppDataBase
import com.example.employee_manager_l2_task.dataBase.AppRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

abstract class BaseViewModel(app : Application) : AndroidViewModel(app) {

    protected val repo = AppRepository(AppDataBase.get(app))

    private val _message = MutableSharedFlow<String>(extraBufferCapacity = 8)

    val message : SharedFlow<String> = _message.asSharedFlow()

    protected fun post(text : String) { _message.tryEmit(text)}

    protected fun launchSafely(onSuccess : () -> Unit ={} ,block : suspend() -> Unit){
        viewModelScope.launch {
            try{
                block()
                onSuccess()
            }
            catch (e : CancellationException){
                throw e
            }
            catch (e : SQLiteConstraintException){
                val m = e.message.orEmpty()
                post(
                    when{
                        "UNIQUE" in m -> "A record with the same value already exists"
                        "FOREIGN" in m -> "this recors is linked to other recordsand cannot be changed or deleted."
                        else -> "Database constraint Failed"
                    }
                )
            }
            catch (e : Exception){
                post("Database error : ${e.localizedMessage ?: "unknown"}")
            }
        }
    }

    protected fun <T> Flow<T>.asSafeLiveData(): LiveData<T> =
        catch { post("Database error: ${it.localizedMessage ?: "unknown"}") }.asLiveData()
}