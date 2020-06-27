package com.berni.android.prototype1lanbase.db

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId


object Test {




    fun setCounter() {

         val time2 =   LocalDateTime.now(ZoneId.of("Europe/Paris"))

         if(time!=time2)   {
            time = time2
            number = 0
        }
    }
    var number:       Int = 0
    var acquired :    Int = 0
    var warningTest:  Int = 0

    var time = LocalDateTime.now(ZoneId.of("Europe/Paris"))
    //var tiime = android.icu.util.Calendar.getInstance()

}




