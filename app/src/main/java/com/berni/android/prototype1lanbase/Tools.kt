package com.berni.android.prototype1lanbase
import android.view.inputmethod.EditorInfo
import android.widget.EditText


// generate  word id, so words have to be unique within the same category but
// can be duplicated if in different categories .

fun wordId( catName:String, wordName: String) : String{

    return  catName.plus("_").plus(wordName)
}

fun EditText.onSubmit(func: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            clearFocus() // if needed
            func()
        }
        true
    }
}