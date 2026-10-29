package com.example.employee_manager_l2_task.ui

import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.employee_manager_l2_task.dataBase.DepartMentTable
import com.example.employee_manager_l2_task.dataBase.EmployeeTable
import com.example.employee_manager_l2_task.databinding.ActivityEmployeePageBinding
import com.example.employee_manager_l2_task.util.EXTRA_ID
import com.example.employee_manager_l2_task.util.observeMessages
import com.example.employee_manager_l2_task.util.toast
import com.example.employee_manager_l2_task.viewmodels.EmployeeViewModel

class EmployeePageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmployeePageBinding
    private val viewModel : EmployeeViewModel by viewModels()
    private var employeeId = 0
    private var departments : List<DepartMentTable> = emptyList()
    private var selectedDepartmentId : Int ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        observeMessages(viewModel)

        employeeId = intent.getIntExtra(EXTRA_ID,0)
        title = if(employeeId == 0) "Add Employee" else "Edit Employee"

        //dept dropdow list
        viewModel.departments.observe(this){ list ->
            (binding.spinnerDepartmentName.selectedItem as? DepartMentTable)?.let{selectedDepartmentId = it.departmentId}
            departments = list

            binding.spinnerDepartmentName.adapter =
                ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,list)

            selectDepartment()
            binding.tvNoDepartments.isVisible = list.isEmpty()
            binding.saveBtn.isEnabled = list.isNotEmpty()
        }

        if(employeeId!=0 && savedInstanceState == null){
            viewModel.load(employeeId){ e ->
                if(e == null) { toast("Emplayee not found"); finish() }
                else{
                    binding.emEtName.setText(e.employeeName)
                    binding.emEtEmail.setText(e.email)
                    binding.emEtPhone.setText(e.phoneNumber)
                    selectedDepartmentId = e.departmentId
                    selectDepartment()
                }
            }
        }

        binding.saveBtn.setOnClickListener { save() }
    }

    private fun selectDepartment() {
        val index = departments
            .indexOfFirst { it.departmentId == selectedDepartmentId }
        if(index >= 0) binding.spinnerDepartmentName.setSelection(index)
    }

    private fun save(){
        binding.emTilName.error = null
        binding.emTilEmail.error = null

        val name = binding.emEtName.text.toString().trim()
        val email = binding.emEtEmail.text.toString().trim()
        val phone = binding.emEtPhone.text.toString().trim().ifEmpty { null }
        var valid = true

        if(name.isEmpty()) { binding.emTilName.error = "Employee name is required "; valid = false}
        if(email.isEmpty()) { binding.emTilEmail.error = "Email is required" ; valid =false }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emTilEmail.error = "Enter a Valid email address"; valid = false
        }
        val department = binding.spinnerDepartmentName.selectedItem as? DepartMentTable
        if(department == null) {toast("Please select a department") ;valid= false}
        if(!valid || department == null) return

        viewModel.save(EmployeeTable(employeeId, name, email, phone, department.departmentId)) {
            toast(if (employeeId == 0) "Employee added" else "Employee updated")
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}