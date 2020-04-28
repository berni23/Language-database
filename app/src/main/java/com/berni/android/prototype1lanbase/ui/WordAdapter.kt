package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.adapter_word.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

// WordInCat

class WordAdapter(private val words: List<Word>, private val viewModel: MainViewModel, override val coroutineContext: CoroutineContext):

      RecyclerView.Adapter<WordAdapter.WordViewHolder>(), CoroutineScope   {

    //var wordNameList:MutableList<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {

        return WordViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_word,parent,false))

        }

    override fun getItemCount() = words.size

    @SuppressLint("SetTextI18n")

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

      //  wordNameList?.add(words[position].wordName)

        holder.view.text_view_word.text = " ${words[position].wordName} "  // adding space at the beginning and at the end to avoid text cut
        holder.view.text_view_translation.text = " ${words[position].trans1} "

        holder.view.edit_word.setOnClickListener {

            val popupMenu = PopupMenu(it.context,it)
            popupMenu.setOnMenuItemClickListener { item ->

                when(item.itemId){
                    R.id.edit_word -> {

                        val bundle = bundleOf("word" to words[position])
                        Navigation.findNavController(it).navigate(R.id.actionEditWord, bundle)

                       //  TODO(navigate to word edit fragment ( to be created) )

                        Toast.makeText(it.context,"editing word ..",Toast.LENGTH_SHORT).show()
                        true
                    }

                    R.id.delete_word -> {

                        AlertDialog.Builder(it.context).apply {
                            setTitle("Are you sure?")
                            setMessage("You cannot undo this operation")

                            setPositiveButton("Yes") { _, _ ->

                               runBlocking(Dispatchers.Default){

                                    viewModel.deleteWord(words[position])

                                }

                            }

                            setNegativeButton("No") { _, _ ->

                            }}.create().show()

                        true

                    }
                    else -> false
            }
            }

            popupMenu.inflate(R.menu.menu_single_word)
            popupMenu.show()
        }
    }

    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    }
