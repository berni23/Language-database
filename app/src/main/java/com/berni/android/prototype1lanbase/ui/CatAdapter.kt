package com.berni.android.prototype1lanbase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
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

    override fun getItemCount() = cats.size

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.view.text_view_title.text = cats[position].catName
        holder.view.text_view_note.text = cats[position].catDate

        holder.view.setOnClickListener {

         //   holder.view.text_view_title.text = cats[position].catName
        //    holder.view.text_view_note.text = cats[position].catDate

                 Navigation.findNavController(it).navigate(R.id.actionAddCat)
        }}


        class CatViewHolder(val view: View) : RecyclerView.ViewHolder(view)


}