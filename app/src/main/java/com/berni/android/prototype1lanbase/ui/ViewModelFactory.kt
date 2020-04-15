package com.berni.android.prototype1lanbase.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berni.android.prototype1lanbase.db.Repository
import org.kodein.di.KodeinAware


// Proveïdor de viewmodel perquè no se'n hagi de crear un de nou cada cop

// Donem les dependéncies necessàries a través de

class ViewModelFactory(private val Repos: Repository): ViewModelProvider.NewInstanceFactory()  {

    @Suppress("UNCHECKED_CASTS")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(Repos) as T
    }

}
