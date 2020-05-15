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
import kotlinx.android.synthetic.main.fragment_statistics3.*
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
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        navController = Navigation.findNavController(view)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        APIlib.getInstance().setActiveAnyChartView(lineChart)

        val lineDays = AnyChart.line()
        val dataDays = dataEntries(sortDays())

        lineDays.data(dataDays)
        lineChart.setChart(lineDays)

        changeGraphs.setOnClickListener{

            navController.navigate(R.id.actionMonthlyView)

            }

        super.onViewCreated(view, savedInstanceState)
        }


    private fun sortDays(): MutableList<String> {

        var days = mutableListOf<String>()
        runBlocking(Dispatchers.Default) { days = viewModel.orderDays() }
        days.sort()
        days.sortBy { it.substring(3, 5).toInt() }
        days.sortBy { it.substring(6, 10).toInt() }

        return days
    }

     private fun dataEntries(days: List<String>): ArrayList<DataEntry> {

            var dataDays = ArrayList<DataEntry>()
            var counter = days.groupingBy { it }.eachCount().toList()
            if (counter.size > 100) {  counter = counter.takeLast(100) }
            counter.toMap()
            counter.forEach {dataDays.add(ValueDataEntry(it.first, it.second)) }
            return dataDays

        }
    }




