package com.berni.android.prototype1lanbase.ui


import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_statistics3.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.berni.android.prototype1lanbase.R
import kotlinx.android.synthetic.main.fragment_statistics4.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList

class Statistics4Fragment : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController
    var boolGraph = true


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        APIlib.getInstance().setActiveAnyChartView(lineChart3)
        labelsG2.text ="number of monthly daily added ,\n last 6 active months"
        var lineMonths = AnyChart.line()
        var months = mutableListOf<String>()
        var dataMonths = ArrayList<DataEntry>()

        runBlocking(Dispatchers.Default) {months = viewModel.orderMonths() }

        months.sort()
        months.sortBy {it.substring(3, 7).toInt() }
        var counterMonths = months.groupingBy { it }.eachCount().toList()
        if (counterMonths.size > 6) {counterMonths = counterMonths.takeLast(6) }

        counterMonths.toMap()
        counterMonths.forEach { dataMonths.add(ValueDataEntry(it.first, it.second)) }

        lineMonths.data(dataMonths)
        lineChart3.setChart(lineMonths)

        changeGraphs2.setOnClickListener{

            navController.navigate(R.id.actionDailyView)

            }

        super.onViewCreated(view, savedInstanceState)
        }

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




