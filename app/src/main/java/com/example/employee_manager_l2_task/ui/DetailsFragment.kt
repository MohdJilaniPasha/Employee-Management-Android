package com.example.employee_manager_l2_task.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employee_manager_l2_task.dataBase.EmployeeDetailsView
import com.example.employee_manager_l2_task.databinding.FragmentListBinding
import com.example.employee_manager_l2_task.util.EXTRA_ID
import com.example.employee_manager_l2_task.util.confirmDialog
import com.example.employee_manager_l2_task.util.observeMessages
import com.example.employee_manager_l2_task.viewmodels.DeatailsViewModel


class DetailsFragment : Fragment() {


    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel : DeatailsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.emptyTV.text = "No employee details yet.\nTap + to add designation, salary and joining date."

        val adapter = DetailsAdapter(
            onEdit = { d ->
                startActivity(Intent(requireContext(), DetailsPageActivity::class.java).putExtra(EXTRA_ID, d.detailsId))
            },
            onDelete = ::onDeleteClicked
        )
        binding.depatRCV.layoutManager = LinearLayoutManager(requireContext())
        binding.depatRCV.adapter = adapter

        viewModel.detailsList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.emptyTV.isVisible = it.isEmpty()
        }
        binding.addBtn.setOnClickListener {
            startActivity(Intent(requireContext(), DetailsPageActivity::class.java))
        }
        observeMessages(viewModel)
    }

    private fun onDeleteClicked(d: EmployeeDetailsView) {
        requireContext().confirmDialog(
            "Delete details", "Delete the details record of \"${d.employeeName}\"?"
        ) { viewModel.delete(d.detailsId) }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().title = "Employee Details"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}