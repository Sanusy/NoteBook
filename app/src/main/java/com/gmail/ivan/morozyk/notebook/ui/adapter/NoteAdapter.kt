package com.gmail.ivan.morozyk.notebook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.ivan.morozyk.notebook.R
import com.gmail.ivan.morozyk.notebook.databinding.ItemNoteBinding
import com.gmail.ivan.morozyk.notebook.model.data.Note

class NoteAdapter(private val favoriteClicked: (note: Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

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

    inner class NoteHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {

            with(binding) {
                favoriteButton.setOnClickListener { favoriteClicked(note) }

                noteTitleText.text = note.title
                noteContentText.text = note.content

                favoriteButton.apply {
                    setImageResource(if (note.favorite) R.drawable.icon_favorite else R.drawable.icon_add_to_favorite)
                    isEnabled = !note.favorite
                }
            }
        }
    }
}

