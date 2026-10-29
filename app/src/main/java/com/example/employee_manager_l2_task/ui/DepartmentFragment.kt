package com.example.employee_manager_l2_task.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employee_manager_l2_task.dataBase.DepartMentTable
import com.example.employee_manager_l2_task.databinding.FragmentListBinding
import com.example.employee_manager_l2_task.util.EXTRA_ID
import com.example.employee_manager_l2_task.viewmodels.DepartmentViewModel
import androidx.fragment.app.viewModels
import com.example.employee_manager_l2_task.util.confirmDialog
import com.example.employee_manager_l2_task.util.observeMessages


class DepartmentFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel : DepartmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentListBinding.inflate(inflater ,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emptyTV.text ="No Departments yet. \nTop + to add one."

        val adapter = DepartmentAdapter(
            onEdit = { d ->
                startActivity(Intent(requireContext(), DepartmentDetailsActivity::class.java).putExtra(
                    EXTRA_ID,d.departmentId))
            },
            onDelete = ::onDeleteClicked
        )

        binding.depatRCV.layoutManager = LinearLayoutManager(requireContext())
        binding.depatRCV.adapter = adapter

        viewModel.departments.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.emptyTV.isVisible = it.isEmpty()
        }

        binding.addBtn.setOnClickListener {
            startActivity(Intent(requireContext(), DepartmentDetailsActivity::class.java))
        }
        observeMessages(viewModel)
    }

    private fun onDeleteClicked(d : DepartMentTable){
        viewModel.employeeCount(d.departmentId) { count ->
            val ctx = context ?: return@employeeCount
            if (count == 0) {
                ctx.confirmDialog("Delete department", "Delete \"${d.departmentName}\"?") { viewModel.delete(d) }
            } else {
                ctx.confirmDialog(
                    "Department has employees",
                    "\"${d.departmentName}\" still has $count employee(s), so it can't be deleted on its own.\n\n" +
                            "Delete the department together with all its employees and their details?",
                    "Delete all"
                ) { viewModel.deleteWithEmployees(d) }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        requireActivity().title = "Departments"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}