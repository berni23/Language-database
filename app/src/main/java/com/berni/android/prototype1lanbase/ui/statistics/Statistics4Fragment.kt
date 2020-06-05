package com.berni.android.prototype1lanbase.ui.statistics


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.ui.BaseFragment
import com.berni.android.prototype1lanbase.ui.viewmodel.MainViewModel
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.android.synthetic.main.fragment_statistics4.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import kotlin.collections.ArrayList
import kotlin.math.abs


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

        var date = mutableListOf<String>()
        var dateAcquired = mutableListOf<String>()

        runBlocking(Dispatchers.Default) {
            date = viewModel.orderDays()
            dateAcquired = viewModel.orderAcquired()
        }

        APIlib.getInstance().setActiveAnyChartView(lineChart3)
        val lineMonths = AnyChart.line()
        val dataMonths = arrayMonths(sortMonths(date))
        lineMonths.data(dataMonths)
        lineChart3.setChart(lineMonths)

        if(dateAcquired.isNotEmpty())

        {
            NoChartAcquired.visibility = View.INVISIBLE
            APIlib.getInstance().setActiveAnyChartView(lineChart4)
            val lineMonths2 = AnyChart.line()
            val dataMonths2 = arrayMonths(sortMonths(dateAcquired))
            lineMonths2.data(dataMonths2)
            lineChart4.setChart(lineMonths2)
        }

        changeGraphs2.setOnClickListener {navController.popBackStack()}
        super.onViewCreated(view, savedInstanceState)
    }

    private fun sortMonths(date: MutableList<String>): ArrayList<String> {

        val months = arrayListOf<String>()
        date.forEach {months.add(it.substring(3,it.lastIndex + 1)) }
        months.sort()
        months.sortBy {it.substring(3,7).toInt() }
        return months

    }

    private fun arrayMonths(months: ArrayList<String>): ArrayList<DataEntry> {

        AndroidThreeTen.init(activity)
        var xAxis = mutableListOf<String>()
        val format1 = DateTimeFormatter.ofPattern("MM/yyyy")
        val format2 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val today = LocalDate.now()
        //.plusMonths(7) ( code useful for debugging)
        val firstMonth = LocalDate.parse("01/${months[0]}", format2)
        val dataMonth = ArrayList<DataEntry>()

        var monthsPassed = abs(today.until(firstMonth, ChronoUnit.MONTHS))
        Log.println(Log.INFO, "monthsPassed", monthsPassed.toString())
        if (monthsPassed > 6) { monthsPassed = 6 }

        if (monthsPassed.toInt() == 0) {
            xAxis.add(format1.format(today))
            dataMonth.add(ValueDataEntry(xAxis[0], months.count {it == xAxis[0]}))


        } else {

            for (i in 0..monthsPassed) { xAxis.add(format1.format(today.minusMonths(i))) }

            xAxis = xAxis.asReversed()
            val range = xAxis.size -1

            for (i in 0..range) { dataMonth.add(ValueDataEntry(xAxis[i], months.count {it == xAxis[i]})) }

        }
        return dataMonth
    }

}


    /**    private fun dataMonths(months: List<String>): ArrayList<DataEntry> {

            val dataMonths = ArrayList<DataEntry>()
            var counterMonths = months.groupingBy {it}.eachCount().toList()
            if (counterMonths.size > 6) {counterMonths = counterMonths.takeLast(6) }
            counterMonths.toMap()
            counterMonths.forEach {dataMonths.add(ValueDataEntry(it.first, it.second)) }
            return dataMonths

        }

    **

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
}**/



