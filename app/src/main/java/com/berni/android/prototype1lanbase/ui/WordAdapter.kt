package com.berni.android.prototype1lanbase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.adapter_word.view.*


// WordInCat

class WordAdapter(private val words: List<Word>): RecyclerView.Adapter<WordAdapter.WordViewHolder>()

{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {

        return WordViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_word,parent,false))

        }


    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        holder.view.text_view_word.text = words[position].wordName

    }

    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}