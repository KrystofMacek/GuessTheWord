package com.krystofmacek.guesstheword.screens.game


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.krystofmacek.guesstheword.R
import com.krystofmacek.guesstheword.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    // current word
    private var word = ""
    // current score
    private var score = 0

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // inflate view and setup data binding obj
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        return binding.root

    }

}
