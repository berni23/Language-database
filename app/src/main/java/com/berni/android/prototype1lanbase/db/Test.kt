package com.berni.android.prototype1lanbase.db

import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

object Test {

    fun setCounter() {

        val time2 =  android.icu.util.Calendar.DATE

        if(time!=time2)   {

            time = time2
            number = 0
        }
    }

    var number: Int = 0
    var time = android.icu.util.Calendar.DATE

}





   // val days: SimpleDateFormat = day2-day


  //  val cal: Calendar = Calendar.getInstance()

    //val time2 = cal.time


