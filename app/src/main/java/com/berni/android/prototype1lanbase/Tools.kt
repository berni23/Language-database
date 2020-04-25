package com.berni.android.prototype1lanbase

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

// generate  word id, so words have to be unique within the same category but
// can be duplicated if in different categories .

fun wordId( catName:String, wordName: String) : String{

    return  catName.plus("_").plus(wordName)
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


