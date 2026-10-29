package com.example.employee_manager_l2_task.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.employee_manager_l2_task.dataBase.DepartMentTable
import com.example.employee_manager_l2_task.databinding.ActivityDepartmentDetailsBinding
import com.example.employee_manager_l2_task.util.EXTRA_ID
import com.example.employee_manager_l2_task.viewmodels.DepartmentViewModel
import com.example.employee_manager_l2_task.util.observeMessages
import com.example.employee_manager_l2_task.util.toast

class DepartmentDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDepartmentDetailsBinding
    private val viewModel : DepartmentViewModel by viewModels()
    private var departmentId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartmentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        observeMessages(viewModel)

        departmentId = intent.getIntExtra(EXTRA_ID, 0)
        title = if (departmentId == 0) "Add Department" else "Edit Department"


        if (departmentId != 0 && savedInstanceState == null) {
            viewModel.load(departmentId) { d ->
                if (d == null) { toast("Department not found"); finish() }
                else {
                    binding.depName.setText(d.departmentName)
                    binding.depDescription.setText(d.description)
                }
            }
        }
        binding.saveBtn.setOnClickListener { save() }
    }

    private fun save() {
        binding.dtName.error = null
        val name = binding.depName.text.toString().trim()
        if (name.isEmpty()) {
            binding.dtName.error = "Department name is required"; return
        }
        val desc = binding.depDescription.text.toString().trim().ifEmpty { null }
        viewModel.save(DepartMentTable(departmentId, name, desc)) {
            toast(if (departmentId == 0) "Department added" else "Department updated")
            finish()
        }
    }

}