package com.gmail.ivan.morozyk.notebook

import android.app.Application

class App : Application() {

    companion object {
        var idGenerator = 0
            private set
            get() {
                return field++
            }

        val noteStorage = ArrayList<Note>()
    }
}