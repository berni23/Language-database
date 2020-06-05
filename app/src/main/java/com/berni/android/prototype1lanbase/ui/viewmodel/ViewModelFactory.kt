package com.berni.android.prototype1lanbase.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.berni.android.prototype1lanbase.db.Repository


// Proveïdor de viewmodel perquè no se'n hagi de crear un de nou cada cop


class ViewModelFactory(private val Repos: Repository): ViewModelProvider.NewInstanceFactory()  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            Repos
        ) as T
    }

}
