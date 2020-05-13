package com.berni.android.prototype1lanbase.ui

import android.graphics.Color.red
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.UserDictionary
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.anychart.core.annotations.Line

import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.monthsBetweenDates
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.fragment_statistics2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware

import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.time.Month
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class Statistics2Fragment : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel
    private  var monthYear = listOf<Int>(Calendar.MONTH, Calendar.YEAR)

    var allWords  = listOf<Word>()
    var  monthsRange = listOf<ZonedDateTime?>()
    var entries = mutableListOf<Entry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics2, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        runBlocking(Dispatchers.Default) {

            monthsRange = viewModel.monthsRange()
            var months = monthsRange.elementAt(0)
            val monthsLast = monthsRange.elementAt(1)
            //var yearLoop  = months?.year
           // var monthLoop = months?.monthValue
            var xAxisLabel  = ArrayList<String>()
            //var diff: Int  = monthsLast?.minus(months).

            var diff = monthsBetweenDates(months,monthsLast)

            for(i in 0..(diff+1) ){

                val numWords = viewModel.counterMonths(months!!.monthValue,months.year)
                entries.add(Entry(months.monthValue.toFloat(),numWords.toFloat()))
                xAxisLabel.add(months.month.toString())
                months.plusMonths(1)

            }
            }

        val vl = LineDataSet(entries, "Words Added")


//Part2
//Part3


        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.colorAccent3
        vl.fillAlpha = R.color.colorPrimary

//Part5
        lineChart.xAxis.labelRotationAngle = 0f

//Part6
        lineChart.data = LineData(vl)

//Part7
        lineChart.axisRight.isEnabled = false

//Part8
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

//Part9
        lineChart.description.text = "Days"
        lineChart.setNoDataText("No forex yet!")

//Part10
        lineChart.animateX(1800, Easing.EaseInExpo)

    }

}



