package com.example.employee_manager_l2_task.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.employee_manager_l2_task.dataBase.DepartMentTable
import com.example.employee_manager_l2_task.dataBase.DepartmentWithCount
import com.example.employee_manager_l2_task.dataBase.EmployeeDetailsView
import com.example.employee_manager_l2_task.dataBase.EmployeeTable
import com.example.employee_manager_l2_task.dataBase.EmployeeWithDepartment
import com.example.employee_manager_l2_task.databinding.DepartmentItemBinding
import com.example.employee_manager_l2_task.databinding.ItemEmployeeBinding
import com.example.employee_manager_l2_task.databinding.ItemEmployeeDetailsBinding
import com.example.employee_manager_l2_task.util.formatCurrency
import com.example.employee_manager_l2_task.util.formatDate


private fun <T :Any> diff(sameItem : (T,T) -> Boolean) = object : DiffUtil.ItemCallback<T>(){
    override fun areItemsTheSame(a : T,b:T) = sameItem(a,b)
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem

}
class DepartmentAdapter(private val onEdit : (DepartMentTable) -> Unit,
                        private val onDelete : (DepartMentTable) -> Unit) : ListAdapter<DepartmentWithCount, DepartmentAdapter.VH>(
          diff { a,b ->a.departMentTable.departmentId == b.departMentTable.departmentId}
      ){

    class VH(val b: DepartmentItemBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = VH(DepartmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: DepartmentAdapter.VH, position: Int) {
        val item = getItem(position)
        with(holder.b) {
            dpName.text = item.departMentTable.departmentName
            dpDescription.text = item.departMentTable.description?.takeIf { it.isNotBlank() } ?: "No Description"
            dpCount.text = "Employees : ${item.employeeCount}"
            editBtn.setOnClickListener { onEdit(item.departMentTable) }
            deleteBtn.setOnClickListener { onDelete(item.departMentTable) }
        }
    }

}

class EmployeeAdapter(
    private val onEdit : (EmployeeTable) -> Unit ,
    private val onDelete : (EmployeeTable) -> Unit
) : ListAdapter<EmployeeWithDepartment , EmployeeAdapter.VH>(
    diff{a,b  -> a.employee.employeeId == b.employee.employeeId}
) {

    class VH (val b : ItemEmployeeBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
          VH(ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context),parent,false))



    override fun onBindViewHolder(holder: EmployeeAdapter.VH, position: Int) {
        val item = getItem(position)
        with(holder.b){
            emName.text = item.employee.employeeName
            emEmail.text = item.employee.email
            emPhone.text = "Phone: "+(item.employee.phoneNumber?.takeIf { it.isNotBlank() } ?: "-")
            emDepartment.text = "Department: ${item.department.departmentName}"
            editBtn.setOnClickListener { onEdit(item.employee) }
            deleteBtn.setOnClickListener { onDelete(item.employee) }
        }
    }
}

class DetailsAdapter(
    private val onEdit: (EmployeeDetailsView) -> Unit,
    private val onDelete: (EmployeeDetailsView) -> Unit
) : ListAdapter<EmployeeDetailsView, DetailsAdapter.VH>(
    diff{ a,b -> a.detailsId == b.detailsId }
){
    class VH(val b : ItemEmployeeDetailsBinding) :  RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemEmployeeDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        with(holder.b) {
            emName.text = item.employeeName
            emDepartment.text = "Department: ${item.departmentName}"
            emDesignation.text = "Designation: ${item.designation}"
            emSalary.text = "Salary: ${formatCurrency(item.salary)}"
            emJoining.text = "Joined: ${formatDate(item.joiningDate)}"
            editBtn.setOnClickListener { onEdit(item) }
            deleteBtn.setOnClickListener { onDelete(item) }
        }
    }
}


