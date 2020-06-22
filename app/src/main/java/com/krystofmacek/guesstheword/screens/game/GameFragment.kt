package com.krystofmacek.guesstheword.screens.game


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.NavHostFragment
import com.krystofmacek.guesstheword.R
import com.krystofmacek.guesstheword.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel

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

        // Initialize ViewModelProvider
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Attach Observers to LiveData
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })
        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord
        })
        viewModel.eventGameFinished.observe(viewLifecycleOwner, Observer { hasFinished ->
            if(hasFinished)     gameFinished()
        })

        // Setup click Listeners
        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }

        Log.i("GameFragment", "GameFragment Created")
        return binding.root

    }

    private fun onSkip() {
        viewModel.onSkip()
    }

    private fun onCorrect() {
        viewModel.onCorrect()
    }

    private fun onEndGame() {
        gameFinished()
    }

    private fun gameFinished() {
        Toast.makeText(activity, "Game Finished", Toast.LENGTH_LONG).show()
        // Pass the score into Score Fragment / ?: null safe - in case of null pass 0
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value ?: 0)
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameFinishedComplete()
    }

}
