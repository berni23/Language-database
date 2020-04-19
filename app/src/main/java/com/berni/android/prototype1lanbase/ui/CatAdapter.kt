package com.berni.android.prototype1lanbase.ui

import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import kotlinx.android.synthetic.main.adapter_cat.view.*


class CatAdapter(private val cats: List<Cat>) : RecyclerView.Adapter<CatAdapter.CatViewHolder>(),
    View.OnCreateContextMenuListener {

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
        holder.view.text_view_title.text = cats[position].catName
        holder.view.text_view_date.text = cats[position].catDate


        holder.view.setOnClickListener {

            val bundle = bundleOf("categoryName" to cats[position].catName)

            findNavController(it).navigate(R.id.actionAddCat, bundle)
        }


        holder.view.setOnCreateContextMenuListener { menu: ContextMenu?, v: View?, menuInfo: ContextMenuInfo? ->

            val info = menuInfo as AdapterView.AdapterContextMenuInfo


            menu?.add("delete")?.setOnMenuItemClickListener {
                Toast.makeText(v?.context, "deleting..", Toast.LENGTH_SHORT).show()
                true
            }


            menu?.add("rename")?.setOnMenuItemClickListener {
                Toast.makeText(v?.context, "renaming..", Toast.LENGTH_SHORT).show()
                true
            }

        }
    }


    class CatViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}




