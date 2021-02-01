package com.gmail.ivan.morozyk.notebook.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.commit
import com.gmail.ivan.morozyk.notebook.R
import com.gmail.ivan.morozyk.notebook.model.data.Note
import com.gmail.ivan.morozyk.notebook.databinding.FragmentNoteListBinding
import com.gmail.ivan.morozyk.notebook.mvp.contract.NoteListContract
import com.gmail.ivan.morozyk.notebook.mvp.presenter.NoteListPresenter
import com.gmail.ivan.morozyk.notebook.ui.adapter.NoteAdapter
import moxy.presenter.InjectPresenter

class NoteListFragment : BaseFragment<FragmentNoteListBinding>(), NoteListContract.View {

    @InjectPresenter
    lateinit var presenter: NoteListPresenter

    private lateinit var adapter: NoteAdapter

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) = FragmentNoteListBinding.inflate(inflater, container, attachToRoot)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        adapter = NoteAdapter(presenter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_note_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.show_todo_item -> {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    addToBackStack(null)
                    replace(R.id.fragment_container, ToDoFragment.newInstance())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            noteListRecycler.adapter = adapter

            addNoteFab.setOnClickListener {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    addToBackStack(null)
                    replace(R.id.fragment_container, AddNoteFragment.newInstance())
                }
            }
        }
    }

    override fun showEmpty() {
        binding.noNotesText.visibility = View.VISIBLE
        binding.noteListRecycler.visibility = View.GONE
    }

    override fun showNotes(noteList: List<Note>) {
        binding.noNotesText.visibility = View.GONE
        binding.noteListRecycler.visibility = View.VISIBLE
        adapter.setNotes(noteList)
    }

    companion object {
        fun newInstance() = NoteListFragment()
    }
}
