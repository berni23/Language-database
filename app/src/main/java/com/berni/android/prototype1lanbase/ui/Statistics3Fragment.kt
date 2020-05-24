package com.berni.android.prototype1lanbase.ui


import android.content.Context
import android.os.Bundle
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
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
        APIlib.getInstance().setActiveAnyChartView(lineChart)

        val lineDays = AnyChart.line()
        val dataDays = dataEntries(sortDays())

        lineDays.data(dataDays)
        lineChart.setChart(lineDays)

        changeGraphs.setOnClickListener{ navController.navigate(R.id.actionMonthlyView) }
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

            val dataDays = ArrayList<DataEntry>()
            var counter = days.groupingBy { it }.eachCount().toList()
            if (counter.size > 100) {  counter = counter.takeLast(100) }
            counter.toMap()
            counter.forEach {dataDays.add(ValueDataEntry(it.first, it.second)) }
            return dataDays

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

        when (item.itemId) {R.id.item_backS3 -> {navController.popBackStack()} }

        return super.onOptionsItemSelected(item)
    }
}





