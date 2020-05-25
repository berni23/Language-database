package com.berni.android.prototype1lanbase.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.fragment_statistics3.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.berni.android.prototype1lanbase.R
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.threeten.bp.LocalDateTime
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.lang.Math.abs
import kotlin.collections.ArrayList

class Statistics3Fragment : BaseFragment(),KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_statistics3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        var days = ArrayList<String>()
        var daysAcquired = ArrayList<String>()

        runBlocking(Dispatchers.Default) {

            daysAcquired =viewModel.orderAcquired() as ArrayList<String>
            days = viewModel.orderDays() as ArrayList<String>

        }

        Log.println(Log.INFO,"acquired",daysAcquired.toString())

        APIlib.getInstance().setActiveAnyChartView(lineChart)

        val lineDays = AnyChart.line()
        val dataDays = arrayDays(sortDays(days))
       // lineDays.data(dataDays)
        lineDays.line(dataDays)
        lineChart.setChart(lineDays)

        APIlib.getInstance().setActiveAnyChartView(lineChart2)
        val lineDays2 = AnyChart.line()
        val dataDays2 = arrayDays(sortDays(daysAcquired))
        lineDays2.data(dataDays2)
        lineChart2.setChart(lineDays2)
        //lineChart.setChart(lineDays2)

        changeGraphs.setOnClickListener {navController.navigate(R.id.actionMonthlyView)}
        super.onViewCreated(view, savedInstanceState)

    }

    private fun sortDays(days: ArrayList<String>): ArrayList<String> {

        days.sort()
        days.sortBy {it.substring(3, 5).toInt()}
        days.sortBy {it.substring(6, 10).toInt()}

        Log.println(Log.INFO,"days",days.toString())
        return days
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                navController.navigate(R.id.actionBackToMainS3)
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

        when (item.itemId) {R.id.item_backS3 -> { navController.popBackStack() } }


        return super.onOptionsItemSelected(item)
    }

    private fun arrayDays(days: ArrayList<String>): ArrayList<DataEntry> {

        AndroidThreeTen.init(activity)
        val today = LocalDate.now()
        var xAxis = mutableListOf<String>()
        val format1 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val firstDay = LocalDate.parse(days[0], format1)
        val dataDays = ArrayList<DataEntry>()
        var daysPassed = abs(today.until(firstDay, ChronoUnit.DAYS))


        if (daysPassed > 100) { daysPassed = 100 }
        if (daysPassed.toInt() == 0) {

            xAxis.add(format1.format(today))
            dataDays.add(ValueDataEntry(xAxis[0], days.count { it == xAxis[0] }))

        } else {


            for (i in 0..daysPassed) { xAxis.add(format1.format(today.plusDays(-i))) }

            xAxis = xAxis.asReversed()
            val last = xAxis.size-1
            for (i in 0..last) { dataDays.add(ValueDataEntry(xAxis[i], days.count {it == xAxis[i]})) }

        }
            return dataDays
        }
}




