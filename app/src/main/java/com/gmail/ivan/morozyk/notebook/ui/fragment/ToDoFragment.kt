package com.gmail.ivan.morozyk.notebook.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.ivan.morozyk.notebook.R
import com.gmail.ivan.morozyk.notebook.databinding.FragmentToDoBinding
import com.gmail.ivan.morozyk.notebook.model.data.ToDo
import com.gmail.ivan.morozyk.notebook.mvp.contract.ToDoContract
import com.gmail.ivan.morozyk.notebook.mvp.presenter.ToDoPresenter
import com.gmail.ivan.morozyk.notebook.ui.adapter.ToDoAdapter
import moxy.presenter.InjectPresenter

class ToDoFragment : BaseFragment<FragmentToDoBinding>(), ToDoContract.View {

    @InjectPresenter
    lateinit var presenter: ToDoPresenter

    private lateinit var adapter: ToDoAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): FragmentToDoBinding {
        return FragmentToDoBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ToDoAdapter { presenter.markAsCompletedButtonClicked(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.todoRecycler.adapter = adapter
    }

    override fun showTODOs(toDoList: List<ToDo>) {
        with(binding) {
            noToDoList.visibility = View.GONE
            todoRecycler.visibility = View.VISIBLE
        }
        adapter.setTODOs(toDoList)
    }

    override fun showLoadingError() {
        showErrorView()
        binding.noToDoList.text = getString(R.string.todo_list_loading_error_string)
    }

    override fun showUpdateError() {
        Toast.makeText(requireContext(), R.string.todo_list_update_error_string, Toast.LENGTH_LONG)
            .show()
    }

    override fun showEmpty() {
        showErrorView()
        binding.noToDoList.text = getString(R.string.todo_list_loading_error_string)
    }

    private fun showErrorView() {
        with(binding) {
            noToDoList.visibility = View.VISIBLE
            todoRecycler.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance() = ToDoFragment()
    }
}