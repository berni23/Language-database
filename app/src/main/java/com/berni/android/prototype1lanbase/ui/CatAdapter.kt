package com.berni.android.prototype1lanbase.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import kotlinx.android.synthetic.main.adapter_cat.view.*
import kotlinx.coroutines.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

class CatAdapter(private val cats: List<Cat>, val viewModel: MainViewModel,
                 override val coroutineContext: CoroutineContext
) : RecyclerView.Adapter<CatAdapter.CatViewHolder>(),
    View.OnCreateContextMenuListener, CoroutineScope {


    var deleteCat : Cat? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {


        return CatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_cat, parent, false)
        )
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View?, menuInfo: ContextMenuInfo?) {

        MenuInflater(v?.context).inflate(R.menu.menu_cat, menu)

    }

    override fun getItemCount() = cats.size


    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {

        holder.view.setOnCreateContextMenuListener(this)
        holder.view.text_view_title.setText(cats[position].catName)
        holder.view.text_view_date.text = cats[position].catDate


        // holder.view.text_view_title.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER)

        holder.view.setOnClickListener {

            val bundle = bundleOf("categoryName" to cats[position].catName)

            findNavController(it).navigate(R.id.actionAddCat, bundle)
        }

        holder.view.setOnCreateContextMenuListener { menu: ContextMenu?, v: View?, menuInfo: ContextMenuInfo? ->

            menu?.add("delete")?.setOnMenuItemClickListener {


                //ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

                AlertDialog.Builder(v?.context).apply {
                    setTitle("Are you sure?")
                    setMessage("You cannot undo this operation")


                    setPositiveButton("Yes") { _, _ ->

                        GlobalScope.launch(Dispatchers.Default){

                            viewModel.deleteWordsInCat(cats[position].catName)
                            viewModel.deleteCat(cats[position])



                        }
                        Toast.makeText(v?.context, "deleting confirmed..", Toast.LENGTH_SHORT).show()

                    }

                    setNegativeButton("No") { _, _ ->

                    }}.create().show()



                true

            }
            menu?.add("rename")?.setOnMenuItemClickListener {

                //TODO : change the name in the database when the category has been renamed

                holder.view.text_view_title.setFocusable(true)
                holder.view.text_view_title.isFocusableInTouchMode = true

                Toast.makeText(v?.context, "renaming..", Toast.LENGTH_SHORT).show()
                true
            }
        }

    }

    suspend fun deleteWordsAndCat() : Cat? {

        return deleteCat


    }

    class CatViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}








