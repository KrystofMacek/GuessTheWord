package com.krystofmacek.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    // Mutable "word" data, to work within class
    private var _word = MutableLiveData<String>()
    // Backing property for "word" field, to read data
    val word: LiveData<String>
        get() = _word

    // Mutable "score" data, to work within class
    private var _score = MutableLiveData<Int>()
    // Backing property for "score" field, to read data
    val score: LiveData<Int>
        get() = _score

    // Mutable "event" data, changes to notify about game ending
    private var _eventGameFinished = MutableLiveData<Boolean>()
    // Backing property
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished


    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel Created")

        // Initialize LiveData
        _word.value = ""
        _score.value = 0

        resetList()
        nextWord()
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

    private fun nextWord() {
        if (wordList.isNotEmpty()) {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        } else {
            onGameFinished()
        }
    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    // Trigger the game finished event
    private fun onGameFinished() {
        _eventGameFinished.value = true
    }
    fun onGameFinishedComplete() {
        _eventGameFinished.value = false
    }
}