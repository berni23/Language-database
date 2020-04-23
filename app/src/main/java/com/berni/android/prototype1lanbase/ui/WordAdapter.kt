package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
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

    override fun getItemCount() = words.size?:0

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        holder.view.text_view_word.text = " ${words.get(position).wordName} "  // adding space at the beginning and at the end to avoid text cut
        holder.view.text_view_translation.text = " ${words.get(position).trans1} "

    }

    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}