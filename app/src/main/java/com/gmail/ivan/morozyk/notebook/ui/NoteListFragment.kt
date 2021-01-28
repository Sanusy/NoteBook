package com.gmail.ivan.morozyk.notebook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ivan.morozyk.notebook.Note
import com.gmail.ivan.morozyk.notebook.R
import com.gmail.ivan.morozyk.notebook.databinding.FragmentNoteListBinding
import com.gmail.ivan.morozyk.notebook.databinding.ItemNoteBinding
import com.gmail.ivan.morozyk.notebook.mvp.NoteListContract
import com.gmail.ivan.morozyk.notebook.mvp.NoteListPresenter
import moxy.presenter.InjectPresenter

class NoteListFragment : BaseFragment<FragmentNoteListBinding>(), NoteListContract.View {

    @InjectPresenter
    lateinit var presenter: NoteListPresenter

    private var adapter: NoteAdapter = NoteAdapter()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) = FragmentNoteListBinding.inflate(inflater, container, attachToRoot)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noteListRecycler.adapter = adapter

        binding.addNoteFab.setOnClickListener {
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                addToBackStack(null)
                replace(R.id.fragment_container, AddNoteFragment.newInstance())
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

    inner class NoteAdapter : RecyclerView.Adapter<NoteHolder>() {

        private val noteList = ArrayList<Note>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            holder.bind(noteList[position])
        }

        override fun getItemCount() = noteList.size

        fun setNotes(notesToDisplay: List<Note>) {
            noteList.clear()
            noteList.addAll(notesToDisplay)
            noteList.sortWith { firstNote, secondNote ->
                if (firstNote.favorite && !secondNote.favorite) -1
                else if (!firstNote.favorite && secondNote.favorite) 1
                else firstNote.id - secondNote.id
            }
            notifyDataSetChanged()
        }
    }

    inner class NoteHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.favoriteButton.setOnClickListener { presenter.addToFavorite(note.id) }
        }

        private lateinit var note: Note

        fun bind(note: Note) {
            this.note = note

            binding.noteTitleText.text = note.title
            binding.noteContentText.text = note.content

            binding.favoriteButton.apply {
                setImageResource(if (note.favorite) R.drawable.icon_favorite else R.drawable.icon_add_to_favorite)
                isEnabled = !note.favorite
            }
        }
    }

    companion object {
        fun newInstance() = NoteListFragment()
    }
}
