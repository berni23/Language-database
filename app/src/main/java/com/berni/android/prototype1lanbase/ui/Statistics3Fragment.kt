package com.berni.android.prototype1lanbase.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_statistics3.*
import androidx.lifecycle.ViewModelProvider
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.berni.android.prototype1lanbase.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList

class Statistics3Fragment : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics3, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        APIlib.getInstance().setActiveAnyChartView(lineChart)
        val line: Cartesian = AnyChart.line()
        var months = mutableListOf<String>()
        val data =  ArrayList<DataEntry>()

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        runBlocking(Dispatchers.Default) { months  = viewModel.orderMonths() }

        months.sort()
        months.sortBy{it.substring(3,7).toInt()}
        var counter  = months.groupingBy{it}.eachCount().toList()
        if (counter.size>6)  {counter = counter.takeLast(6)}

        counter.toMap()
        counter.forEach{

            data.add(ValueDataEntry(it.first,it.second))

        }
        
        line.data(data)
        lineChart.setChart(line)
        super.onViewCreated(view, savedInstanceState)
    }

    /**var theList = myArray

    println(theList)
    theList.sort()
    println(theList)
    theList.sortBy{it.substring(3,7).toInt()}
    println(theList)


    var dates = mutableListOf("01/01/2001","01/01/2001","30/03/2012", "30/03/2012","28/03/2013", "31/03/2012", "02/04/2012","04/02/2012","28/03/2011")


    dates.sort()
    dates.sortBy{it.substring(3,5).toInt()}
    dates.sortBy{it.substring(6,10).toInt()}


    val counter  = dates.groupingBy{it}.eachCount()
    println(counter[dates[1]])
    println(counter)

    dates = dates.distinct() as MutableList<String>
    println(dates)

    for (i in  0 until theList.lastIndex) {

        val n = theList.elementAt(i)
        val nPlus1 = theList.elementAt(i + 1)
        if (n.substring(3, 7) == nPlus1.substring(3, 7)) {
            if (n.substring(0, 2) > nPlus1.substring(0, 2)) {

                Collections.swap(theList,i,i+1)
            }
        }
    }
}
**/
}



