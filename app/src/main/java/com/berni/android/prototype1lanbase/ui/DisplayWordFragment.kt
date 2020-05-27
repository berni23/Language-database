package com.berni.android.prototype1lanbase.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.berni.android.prototype1lanbase.R
import com.berni.android.prototype1lanbase.db.Word
import kotlinx.android.synthetic.main.fragment_display_word.*

/**
 * A simple [Fragment] subclass.
 */
class DisplayWordFragment : BaseFragment() {

    private lateinit var word: Word
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        word = arguments?.get("displayWord") as Word
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_display_word, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        (activity as AppCompatActivity).supportActionBar?.title = word.wordName

        //mandatory fields

        textView_displayWord.text = " ${word.wordName} "
        textView_displayTranslation.text = " ${word.trans1} "
        textView_displayDate.text = "${resources.getString(R.string.added_on)} ${word.date} "

        //optional fields

       if (word.ex1!=null) { textView_displayEx1.text = " ${word.ex1} " }
       if(word.trans_ex1!=null) { textView_displayEx1Translation.text = " ${word.trans_ex1} " }
       if(word.definition!=null) { textView_displayDefinition.text = " ${word.definition} " }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_words, menu)

        menu.findItem(R.id.item_backEditToWordsList).setIcon(R.drawable.ic_arrow_downward_white_24dp)
        return super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {R.id.item_backEditToWordsList -> { navController.popBackStack() } }
        return super.onOptionsItemSelected(item)
    }

}

