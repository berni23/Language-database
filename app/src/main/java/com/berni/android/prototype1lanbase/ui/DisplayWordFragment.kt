package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.fragment_display_word.*

/**
 * A simple [Fragment] subclass.
 */
class DisplayWordFragment : BaseFragment() {

    private lateinit var word: Word

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        word = arguments?.get("displayWord") as Word
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_word, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = word.wordName

        //mandatory fields

        textView_displayWord.setText(" ${word.wordName} ")
        textView_displayTranslation.setText(" ${word.trans1} ")
        textView_displayDate.setText("added on ${word.date} ")

        //optional fields

       if (word.ex1!=null) {textView_displayEx1.setText(" ${word.ex1} ")}
       if(word.trans_ex1!=null) {textView_displayEx1Translation.setText(" ${word.trans_ex1} ")}
       if(word.definition!=null) {textView_displayDefinition.setText(" ${word.definition} ")}

    }
}
