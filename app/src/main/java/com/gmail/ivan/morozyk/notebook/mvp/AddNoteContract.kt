package com.gmail.ivan.morozyk.notebook.mvp

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface AddNoteContract {

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {
        fun showTitleError()

        fun showContentError()

        fun back()
    }

    interface Presenter {
        fun saveNote(noteTitle: String, noteContent: String)
    }
}