package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.adapter_word.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext


// WordInCat

class WordAdapter(private val words: List<Word>, override val coroutineContext: CoroutineContext): RecyclerView.Adapter<WordAdapter.WordViewHolder>(),Filterable,
    CoroutineScope
{

    var wordNameList:MutableList<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {

        return WordViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_word,parent,false))

        }

    override fun getItemCount() = words.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        wordNameList?.add(words[position].wordName)

        holder.view.text_view_word.text = " ${words[position].wordName} "  // adding space at the beginning and at the end to avoid text cut
        holder.view.text_view_translation.text = " ${words[position].trans1} "

    }

    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun getFilter(): Filter {

        return myFilter
    }

    private var myFilter: Filter = object : Filter() {
        //Automatic on background thread
        override fun performFiltering(charSequence: CharSequence): FilterResults {

            val filteredList: MutableList<String> = ArrayList()
            if (charSequence.isEmpty()) {
                filteredList.addAll(wordNameList!!)
            } else {
                for (word in wordNameList!!) {
                    if (word.toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(
                            Locale.ROOT
                        )
                        )
                    ) {
                        filteredList.add(word)
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        //Automatic on UI thread
        override fun publishResults(

            charSequence: CharSequence,
            filterResults: FilterResults
        ) {
            
            wordNameList?.clear()
            wordNameList?.addAll(filterResults.values as Collection<String>)
            notifyDataSetChanged()
        }
    }


}