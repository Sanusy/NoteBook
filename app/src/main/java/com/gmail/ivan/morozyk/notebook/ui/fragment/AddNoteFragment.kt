package com.gmail.ivan.morozyk.notebook.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.ivan.morozyk.notebook.R
import com.gmail.ivan.morozyk.notebook.databinding.FragmentAddNoteBinding
import com.gmail.ivan.morozyk.notebook.mvp.contract.AddNoteContract
import com.gmail.ivan.morozyk.notebook.mvp.presenter.AddNotePresenter
import moxy.presenter.InjectPresenter

class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(), AddNoteContract.View {

    @InjectPresenter
    lateinit var presenter: AddNotePresenter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): FragmentAddNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, attachToRoot)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveNoteFab.setOnClickListener {
            presenter.saveNote(
                binding.noteTitle.text.toString(),
                binding.noteContent.text.toString()
            )
        }
    }

    override fun showTitleError() {
        binding.noteTitle.error = getString(R.string.add_note_no_title_error)
    }

    override fun showContentError() {
        binding.noteContent.error = getString(R.string.add_note_no_content_error)
    }

    override fun back() {
        parentFragmentManager.popBackStack()
    }

    companion object {
        fun newInstance() = AddNoteFragment()
    }
}