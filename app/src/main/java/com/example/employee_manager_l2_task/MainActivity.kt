package com.example.employee_manager_l2_task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.employee_manager_l2_task.databinding.ActivityMainBinding
import com.example.employee_manager_l2_task.ui.DepartmentFragment
import com.example.employee_manager_l2_task.ui.DetailsFragment
import com.example.employee_manager_l2_task.ui.EmployeeFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
       if(savedInstanceState == null)
          showPage(DepartmentFragment())

        binding.bottomNavBar.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_departments -> showPage(DepartmentFragment())
                R.id.nav_details  -> showPage(DetailsFragment())
                R.id.nav_employees -> showPage(EmployeeFragment())
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    fun showPage(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer,fragment).commit()
    }
}