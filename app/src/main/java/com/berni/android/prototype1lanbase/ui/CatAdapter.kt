package com.berni.android.prototype1lanbase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Cat
import kotlinx.android.synthetic.main.adapter_cat.view.*


class CatAdapter(private val cats: List<Cat>) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_cat, parent, false)
        )
    }

    override fun getItemCount() = cats.size?:0

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {

        holder.view.text_view_title.text = cats[position].catName
        holder.view.text_view_date.text = cats[position].catDate

        holder.view.setOnClickListener {

           // val action = FirstFragmentDirections.actionAddNote()
            //val action = Firs.actionAddNote()
           // action.categoryName = cats[position].catName

            val bundle = bundleOf("categoryName" to cats[position].catName)

            holder.view.text_view_title.text = cats[position].catName
            holder.view.text_view_date.text = cats[position].catDate

            findNavController(it).navigate(R.id.actionAddCat,bundle)

        }}


        class CatViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}