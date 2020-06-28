package com.berni.android.prototype1lanbase

import android.app.Activity
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.berni.android.prototype1lanbase.db.Word
import java.util.*


/** Set of functions and variables used throughout the different packages

-----------------------------------
-----------------------------------
1 - variables
-----------------------------------
-----------------------------------
**/



var limitNotAcquired = 120  // change the number on every translation in strings folder as well
var limitWarning = 24


/**
-----------------------------------
-----------------------------------
  2 - Hide and show Keyboard
-----------------------------------
-----------------------------------
**/

fun Fragment.hideKeyboard() {
    view?.let {activity?.hideKeyboard(it)}
}

/**
 * fun Fragment.showKeyboard() {

    view?.let{activity?.showKeyboard(it)}
}

**/

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, 0)
}




/**
-----------------------------------
-----------------------------------
3 - Filtering and sorting functions
-----------------------------------
-----------------------------------

**/

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

fun sortLastAdded(displayedWords: List<Word>): List<Word> { return displayedWords }

fun sortFirstAdded(displayedWords: List<Word>) : List<Word>{ return displayedWords.reversed() }

fun sortAlphabetically(displayedWords: List<Word>) : List<Word>{

    val displayed = displayedWords.toTypedArray()
    return displayed.sortedBy {it.wordName.toLowerCase(Locale.ROOT) }

}

fun sortByLength(displayedWords: List<Word>) : List<Word> {

    val displayed = displayedWords.toTypedArray()
    return displayed.sortedBy {it.wordName.length}

}

// disable visibility items when using searchbar


fun setItemsVisibility(menu: Menu, no: MenuItem, bool: Boolean) {

    val size = menu.size()-1
    for  (i in  0..size) {

        val item =  menu.getItem(i)

        if ( item!=no) { item.isVisible = bool }

    }



}



//number of months between two dates

/**
@RequiresApi(Build.VERSION_CODES.O)
fun monthsBetweenDates(startDate: ZonedDateTime?, endDate: ZonedDateTime?): Int {

    var monthsBetween: Int =0
    monthsBetween += endDate?.monthValue?.minus(startDate?.monthValue!!)!!
    monthsBetween += (endDate.year.minus(startDate?.year!!)* 12)
    return monthsBetween
}

 object LocalDateTimeConverter {
@RequiresApi(Build.VERSION_CODES.O)
@TypeConverter
fun toDate(dateString: String?): LocalDateTime? {
return if (dateString == null) {
null
} else {
LocalDateTime.parse(dateString)
}
}

@TypeConverter
fun toDateString(date: LocalDateTime?): String? {
return date?.toString()
}
}
 **/



//MIGRATION DATABASE

/** private val MIGRATION_9_10 = object : Migration(9, 10) {
override fun migrate(database: SupportSQLiteDatabase) {
database.execSQL("""
CREATE TABLE new_Cat (
catName TEXT,
catDate TEXT,
catId INTEGER PRIMARY KEY NOT NULL,
tag TEXT NOT NULL DEFAULT ''

""".trimIndent())
database.execSQL("""
INSERT INTO new_Cat (catName, catDate, catId,tag)
SELECT catName, catDate,catId, tag FROM Cat
""".trimIndent())
database.execSQL("DROP TABLE Cat")
database.execSQL("ALTER TABLE new_Cat RENAME TO Cat")
}
}**/

/**private val MIGRATION_9_10 = object : Migration(1, 2) {
override fun migrate(database: SupportSQLiteDatabase) {
database.execSQL(
"ALTER TABLE WORD ADD COLUMN tag TEXT NOT NULL DEFAULT ''"
)

}
}**/

