package com.example.employee_manager_l2_task.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employee_manager_l2_task.dataBase.EmployeeTable
import com.example.employee_manager_l2_task.databinding.FragmentListBinding
import com.example.employee_manager_l2_task.util.EXTRA_ID
import com.example.employee_manager_l2_task.util.confirmDialog
import com.example.employee_manager_l2_task.util.observeMessages
import com.example.employee_manager_l2_task.viewmodels.EmployeeViewModel


class EmployeeFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmployeeViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBar.isVisible = true

        val adapter = EmployeeAdapter(
            onEdit = { e ->
                startActivity(Intent(requireContext(), EmployeePageActivity::class.java).putExtra(
                    EXTRA_ID,e.employeeId
                ))
            },
            onDelete = ::onDeleteClicked
        )

        binding.depatRCV.layoutManager = LinearLayoutManager(requireContext())
        binding.depatRCV.adapter = adapter

        viewModel.employees.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyTV.isVisible = list.isEmpty()
            binding.emptyTV.text = if (binding.searchBar.query.isNullOrBlank())
                "No employees yet.\nTap + to add one." else "No employees match your search."
        }

        binding.searchBar.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText.orEmpty()); return true
            }
        })


        binding.addBtn.setOnClickListener {
            startActivity(Intent(requireContext(), EmployeePageActivity::class.java))
        }
        observeMessages(viewModel)

    }
    private fun onDeleteClicked(e: EmployeeTable) {
        requireContext().confirmDialog(
            "Delete employee",
            "Delete \"${e.employeeName}\"? Their details record (if any) will be deleted as well."
        ) { viewModel.delete(e) }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = "Employees"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


