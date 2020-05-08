package com.berni.android.prototype1lanbase

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.berni.android.prototype1lanbase.db.Word
import java.util.*

// generate  word id, so words have to be unique within the same category but
// can be duplicated if in different categories .

fun wordId( catNum:String, wordName: String) : String{

    return  wordName.plus(catNum)
}

// fun for hiding keyboard

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


// tools for filtering and sorting Words List Fragment


 fun filterNoExample(toFilter: List<Word>): List<Word> {

    val filtered = mutableListOf<Word>()

    toFilter.forEach {
        if (it.ex1.isNullOrEmpty()) {
            filtered.add(it)
        }
    }

    return filtered
}

fun filterExample(toFilter: List<Word>): List<Word> {

    val filtered = mutableListOf<Word>()

    toFilter.forEach {
        if (it.ex1 != null) {
            filtered.add(it)
        }
    }
    return filtered
}

fun filterDefinition(toFilter: List<Word>): List<Word>{

    val filtered = mutableListOf<Word>()
    toFilter.forEach {
        if (it.definition != null) {
            filtered.add(it)
        }
    }
    return filtered
}


fun filterNoDefinition(toFilter: List<Word>): List<Word>{

    val filtered = mutableListOf<Word>()
    toFilter.forEach {
        if (it.definition == null) {
            filtered.add(it)
        }
    }
    return filtered
}

fun filterAcquired(toFilter: List<Word>): List<Word> {

    val filtered = mutableListOf<Word>()
    toFilter.forEach {
        if (it.acquired) {
            filtered.add(it)
        }
    }
    return filtered

}


fun filterNotAcquired(toFilter: List<Word>): List<Word> {

    val filtered = mutableListOf<Word>()
    toFilter.forEach {
        if (!it.acquired) {
            filtered.add(it)
        }
    }
    return filtered

}



fun sortLastAdded(displayedWords: List<Word>): List<Word> {

    return displayedWords

}

 fun sortFirstAdded(displayedWords: List<Word>) : List<Word>{

    return displayedWords.reversed()
}

fun sortAlphabetically(displayedWords: List<Word>) : List<Word>{

    val displayed = displayedWords.toTypedArray()
    return displayed.sortedBy {it.wordName.toLowerCase(Locale.ROOT) }

}

fun sortByLength(displayedWords: List<Word>) : List<Word> {

    val displayed = displayedWords.toTypedArray()
    return displayed.sortedBy { it.wordName.length}

}

