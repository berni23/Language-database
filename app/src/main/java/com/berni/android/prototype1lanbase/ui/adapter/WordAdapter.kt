package com.berni.android.prototype1lanbase.ui.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.databinding.AdapterWordBinding
import com.berni.android.prototype1lanbase.db.Word
import com.berni.android.prototype1lanbase.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

// WordInCat

class WordAdapter(private val words: List<Word>, private val viewModel: MainViewModel, private val timers:List<CountDownTimer>, override val coroutineContext: CoroutineContext):

      RecyclerView.Adapter<WordAdapter.WordViewHolder>(), CoroutineScope   {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {

        return WordViewHolder(
            AdapterWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun getItemCount() = words.size

    @SuppressLint("SetTextI18n")

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        //  wordNameList?.add(words[position].wordName)

        holder.binding.textViewWord.text = " ${words[position].wordName} "  // adding space at the beginning and at the end to avoid text cut
        holder.binding.textViewTranslation.text = " ${words[position].trans1} "

        if (words[position].acquired) {

            holder.binding.textViewWord.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.colorHint3))
            holder.binding.textViewTranslation.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.colorHint3))}

        else {
            holder.binding.textViewWord.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.colorWord3))
            holder.binding.textViewTranslation.setTextColor(ContextCompat.getColor(holder.binding.root.context,  R.color.colorButton3))
        }

        holder.binding.editWord.setOnClickListener {

            val popupMenu = PopupMenu(it.context,it)
            popupMenu.setOnMenuItemClickListener { item ->

                when(item.itemId){
                    R.id.edit_word -> {

                        val bundle = bundleOf("word" to words[position])
                        Navigation.findNavController(it).navigate(R.id.actionEditWord, bundle)
                        stopTimers()
                        true

                    }

                    R.id.delete_word -> {

                        AlertDialog.Builder(it.context).apply {
                            setTitle(holder.itemView.context.getString(R.string.are_you_sure))
                            setMessage(holder.itemView.context.getString(R.string.you_cannot_undo))

                            setPositiveButton(holder.itemView.context.getString(R.string.yes)) { _, _ ->

                                runBlocking(Dispatchers.Default){

                                    viewModel.deleteWord(words[position])

                                }
                            }

                            setNegativeButton(holder.itemView.context.getString(R.string.no)) { _, _ -> }

                        }.create().show()

                        true

                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.menu_single_word)
            popupMenu.show()
        }

        holder.binding.root.setOnClickListener{

            val bundle = bundleOf("displayWord" to words[position])
            Navigation.findNavController(it).navigate(R.id.actionDisplayWord, bundle)

            stopTimers()

        }
    }

    class WordViewHolder(val binding: AdapterWordBinding) : RecyclerView.ViewHolder(binding.root)
    private fun stopTimers(){timers.forEach{it.cancel()}}

}


