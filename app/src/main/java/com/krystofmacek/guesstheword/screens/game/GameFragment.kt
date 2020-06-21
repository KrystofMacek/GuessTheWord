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

        resetList()
        nextWord()

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }

        updateScoreText()
        updateWordText()

        return binding.root

    }

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    private fun onSkip() {
        score--
        nextWord()
    }

    private fun onCorrect() {
        score++
        nextWord()
    }

    private fun nextWord() {
        if (!wordList.isEmpty()) {
            //Select and remove a word from the list
            word = wordList.removeAt(0)
        }
        updateWordText()
        updateScoreText()
    }

    private fun updateWordText() {
        binding.wordText.text = word
    }

    private fun updateScoreText() {
        binding.scoreText.text = score.toString()
    }

}
