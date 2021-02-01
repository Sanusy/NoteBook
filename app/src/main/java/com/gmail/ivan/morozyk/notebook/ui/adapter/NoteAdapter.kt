package com.gmail.ivan.morozyk.notebook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ivan.morozyk.notebook.R
import com.gmail.ivan.morozyk.notebook.model.data.Note
import com.gmail.ivan.morozyk.notebook.databinding.ItemNoteBinding
import com.gmail.ivan.morozyk.notebook.mvp.presenter.NoteListPresenter

class NoteAdapter(private val presenter: NoteListPresenter) : RecyclerView.Adapter<NoteHolder>() {

    private val noteList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteHolder(
        ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), presenter
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

class NoteHolder(private val binding: ItemNoteBinding, private val presenter: NoteListPresenter) :
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