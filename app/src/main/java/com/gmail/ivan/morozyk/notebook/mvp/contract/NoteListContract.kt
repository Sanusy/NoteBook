package com.gmail.ivan.morozyk.notebook.mvp.contract

import com.gmail.ivan.morozyk.notebook.model.data.Note
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface NoteListContract {

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {
        fun showEmpty()

        fun showNotes(noteList: List<Note>)
    }

    interface Presenter {
        fun addToFavorite(noteId: Int)
    }
}