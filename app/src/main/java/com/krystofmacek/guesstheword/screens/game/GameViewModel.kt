package com.krystofmacek.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    companion object {
        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L
    }

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

    // Mutable "timer" data
    private var _currentTime = MutableLiveData<Long>()
    // Backing property
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private val timer: CountDownTimer

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel Created")

        // Initialize LiveData
        _word.value = ""
        _score.value = 0

        // init timer
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinished()
            }

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ ONE_SECOND
            }
        }
        timer.start()

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
            resetList()
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
    fun onGameFinished() {
        _eventGameFinished.value = true
    }
    fun onGameFinishedComplete() {
        _eventGameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

}