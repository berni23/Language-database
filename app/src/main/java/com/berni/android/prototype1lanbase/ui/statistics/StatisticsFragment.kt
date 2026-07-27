package com.berni.android.prototype1lanbase.ui.statistics

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.databinding.FragmentStatisticsBinding
import com.berni.android.prototype1lanbase.db.CatWords
import com.berni.android.prototype1lanbase.ui.BaseFragment
import com.berni.android.prototype1lanbase.ui.viewmodel.MainViewModel
import com.berni.android.prototype1lanbase.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : BaseFragment(), DIAware

{
    override val di by closestDI()
    private val viewModelFactory: ViewModelFactory by instance<ViewModelFactory>()
    private lateinit var viewModel: MainViewModel
    private lateinit var catsNwords :List<CatWords>
    private lateinit var counterAcquired: List<Int>
    private lateinit var navController: NavController
    private var numWords: Int = 0
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        (activity as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.statistics)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
       runBlocking(Dispatchers.Default) {

           catsNwords = viewModel.catsNWords()
           counterAcquired = arrayListOf(viewModel.getNumAcquired(true),viewModel.getNumAcquired(false))

       }

        binding.btnTimeProgress.setOnClickListener{ navController.navigate(R.id.actionTimeLine) }
        pieChartAcquired()
        setupPieChart()
        Toast.makeText(context,resources.getString(R.string.msg1_statistics1),Toast.LENGTH_LONG).show()
    }

    private fun setupPieChart() {

        APIlib.getInstance().setActiveAnyChartView(binding.pieChart)
        val pie: Pie = AnyChart.pie()
        pie.legend(false)
        val dataEntries = ArrayList<DataEntry>()

        catsNwords.forEach{

            val length = it.words.size
            dataEntries.add(ValueDataEntry(it.cat.catName,length))
            numWords+=length
        }

        pie.data(dataEntries)
        binding.pieChart.setChart(pie)
        //statistics_numWords.text = "$numWords words added"
       // statistics_numWordsAk.text = "${counterAcquired[0]} words acquired"

    }

    private fun pieChartAcquired() {

        APIlib.getInstance().setActiveAnyChartView(binding.pieChartAcquired)
        val pie2: Pie = AnyChart.pie()
        pie2.legend(false)
        val dataEntries = ArrayList<DataEntry>()
        dataEntries.add(ValueDataEntry(resources.getString(R.string.words_acquired),counterAcquired[0]))
        dataEntries.add(ValueDataEntry(resources.getString(R.string.words_not_acquired),counterAcquired[1]))
        pie2.data(dataEntries)
        binding.pieChartAcquired.setChart(pie2)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_s1, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {R.id.item_backToMainS1 -> {navController.popBackStack()} }

        return super.onOptionsItemSelected(item)
    }
}