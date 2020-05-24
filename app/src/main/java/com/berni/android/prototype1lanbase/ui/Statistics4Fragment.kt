package com.berni.android.prototype1lanbase.ui



import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.berni.android.prototype1lanbase.R
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.fragment_statistics4.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Statistics4Fragment : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavController

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_statistics4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        APIlib.getInstance().setActiveAnyChartView(lineChart3)
        val lineMonths = AnyChart.line()
        val dataMonths = arrayMonths(sortMonths() as ArrayList<String>)
        lineMonths.data(dataMonths)
        lineChart3.setChart(lineMonths)

        changeGraphs2.setOnClickListener{navController.popBackStack() }
        super.onViewCreated(view, savedInstanceState)
        }

    private fun sortMonths(): MutableList<String> {

        var date = listOf<String>()
        val months = arrayListOf<String>()
        runBlocking(Dispatchers.Default) { date = viewModel.orderDays() }
        date.forEach {months.add(it.substring(3, it.lastIndex+1)) }
        months.sort()
        months.sortBy {it.substring(3,7).toInt() }
        return months

    }
    private fun arrayMonths(months: ArrayList<String>): ArrayList<DataEntry> {

        AndroidThreeTen.init(activity)
        val xAxis = ArrayList<String>()
        val format1 = DateTimeFormatter.ofPattern("MM/yyyy")
        val format2 =  DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val today = LocalDate.now().plusMonths(7)
        val firstMonth = LocalDate.parse("01/${months[0]}",format2)
        val dataMonth = ArrayList<DataEntry>()

        var monthsPassed =  Math.abs(today.until(firstMonth, ChronoUnit.MONTHS))
        Log.println(Log.INFO, "monthsPassed", monthsPassed.toString())
        if (monthsPassed > 6) { monthsPassed = 6 }
        if (monthsPassed.toInt() == 0) {

            xAxis.add(format1.format(today))
            dataMonth.add(ValueDataEntry(xAxis[0], months.count {it == xAxis[0]}))

        } else {

            for (i in 0..monthsPassed) {
                xAxis.add(format1.format(today.minusMonths(i)))
            }
            xAxis.reverse()
            val range =  xAxis.size-1
            Log.println(Log.INFO, "range", range.toString())

            for (i in 0..range) { dataMonth.add(ValueDataEntry(xAxis[i], months.count { it == xAxis[i] }))}
            Log.println(Log.INFO, "xAxis.size", xAxis.size.toString())
        }
        return dataMonth
    }

        private fun dataMonths(months: List<String>): ArrayList<DataEntry> {

            val dataMonths = ArrayList<DataEntry>()
            var counterMonths = months.groupingBy {it}.eachCount().toList()
            if (counterMonths.size > 6) {counterMonths = counterMonths.takeLast(6) }
            counterMonths.toMap()
            counterMonths.forEach {dataMonths.add(ValueDataEntry(it.first, it.second)) }
            return dataMonths

        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                navController.navigate(R.id.actionBackToMainS4)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_s3, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {R.id.item_backS3 -> {navController.navigate(R.id.action_BackToS1)} }

        return super.onOptionsItemSelected(item)
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




