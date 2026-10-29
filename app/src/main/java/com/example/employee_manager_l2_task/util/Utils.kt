package com.example.employee_manager_l2_task.util

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.employee_manager_l2_task.viewmodels.BaseViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val EXTRA_ID = "extra_id"

fun Fragment.observeMessages(vm: BaseViewModel) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            vm.message.collect { context?.toast(it) }
        }
    }
}

fun AppCompatActivity.observeMessages(vm: BaseViewModel) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) { vm.message.collect { toast(it) } }
    }
}



fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.confirmDialog(title: String, message: String, positive: String = "Delete", onConfirm: () -> Unit) {
    MaterialAlertDialogBuilder(this)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton("Cancel", null)
        .setPositiveButton(positive) { _, _ -> onConfirm() }
        .show()
}
fun formatCurrency(amount: Double): String =
    NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(amount)

fun formatDate(millis: Long): String =
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(millis))