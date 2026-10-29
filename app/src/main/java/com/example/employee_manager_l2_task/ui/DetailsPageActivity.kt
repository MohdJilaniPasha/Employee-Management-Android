package com.example.employee_manager_l2_task.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

import com.example.employee_manager_l2_task.dataBase.EmployeeDetailsTable
import com.example.employee_manager_l2_task.dataBase.EmployeeTable
import com.example.employee_manager_l2_task.databinding.ActivityDetailsPageBinding
import com.example.employee_manager_l2_task.util.EXTRA_ID
import com.example.employee_manager_l2_task.util.formatDate
import com.example.employee_manager_l2_task.util.observeMessages
import com.example.employee_manager_l2_task.util.toast
import com.example.employee_manager_l2_task.viewmodels.DeatailsViewModel
import java.util.Calendar

class DetailsPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsPageBinding
    private val viewModel : DeatailsViewModel by viewModels()
    private var detailsId = 0
    private var editingEmployeeId = 0
    private var joiningMillis : Long? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        observeMessages(viewModel)

        detailsId = intent.getIntExtra(EXTRA_ID, 0)
        title = if (detailsId == 0) "Add Employee Details" else "Edit Employee Details"
        binding.emEtJoiningDate.setOnClickListener { pickDate() }

        if (detailsId == 0) {
            // Only employees that don't have a details record yet (one-to-one relation)
            viewModel.employeesWithoutDetails.observe(this) { list ->
                binding.spinnerEmployee.adapter =
                    ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
                binding.noEmployeesTV.isVisible = list.isEmpty()
                binding.saveBtn.isEnabled = list.isNotEmpty()
            }
        } else {
            binding.labelEmployee.isVisible = false
            binding.spinnerEmployee.isVisible = false
            binding.employeeNameTV.isVisible = true
            if (savedInstanceState == null) {
                viewModel.load(detailsId) { v ->
                    if (v == null) { toast("Details not found"); finish() }
                    else {
                        editingEmployeeId = v.employeeId
                        binding.employeeNameTV.text = "${v.employeeName} (${v.departmentName})"
                        binding.emEtDesignation.setText(v.designation)
                        binding.emEtSalary.setText(v.salary.toBigDecimal().toPlainString())
                        joiningMillis = v.joiningDate
                        binding.emEtJoiningDate.setText(formatDate(v.joiningDate))
                    }
                }
            }
        }
        binding.saveBtn.setOnClickListener { save() }
    }

    private fun pickDate() {
        val cal = Calendar.getInstance().apply { joiningMillis?.let { timeInMillis = it } }
        DatePickerDialog(this, { _, y, m, d ->
            val picked = Calendar.getInstance().apply { clear(); set(y, m, d) }.timeInMillis
            joiningMillis = picked
            binding.emEtJoiningDate.setText(formatDate(picked))
            binding.emTilJoiningDate.error = null
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun save() {
        binding.emTilDesignation.error = null
        binding.emTilSalary.error = null
        binding.emTilJoiningDate.error = null
        val designation = binding.emEtDesignation.text.toString().trim()
        val salary = binding.emEtSalary.text.toString().trim().toDoubleOrNull()
        var valid = true

        if (designation.isEmpty()) { binding.emTilDesignation.error = "Designation is required"; valid = false }
        if (salary == null || salary <= 0) { binding.emTilSalary.error = "Enter a valid salary greater than 0"; valid = false }
        if (joiningMillis == null) { binding.emTilJoiningDate.error = "Joining date is required"; valid = false }

        val employeeId = if (detailsId == 0) (binding.spinnerEmployee.selectedItem as? EmployeeTable)?.employeeId else editingEmployeeId
        if (employeeId == null || employeeId == 0) { toast("Please select an employee"); valid = false }
        if (!valid) return

        viewModel.save(
            EmployeeDetailsTable(
                detailsId,
                employeeId!!,
                designation,
                salary!!,
                joiningMillis!!
            )
        ) {
            toast(if (detailsId == 0) "Details saved" else "Details updated")
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}