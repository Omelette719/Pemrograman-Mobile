package com.example.diceroller

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var diceRoll = 0
    private var diceRollTwo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener { rollDice() }

        if (savedInstanceState != null) {
            diceRoll = savedInstanceState.getInt("diceRoll", 0)
            diceRollTwo = savedInstanceState.getInt("diceRollTwo", 0)
        }

        updateDiceImages()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("diceRoll", diceRoll)
        outState.putInt("diceRollTwo", diceRollTwo)
    }

    private fun rollDice() {
        val dice = Dice(6)
        val diceTwo = Dice(6)

        diceRoll = dice.roll()
        diceRollTwo = diceTwo.roll()

        updateDiceImages()

        if (diceRoll == diceRollTwo) {
            Toast.makeText(this, "Selamat anda dapat dadu double!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Anda belum beruntung!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateDiceImages() {
        binding.imageView.setImageResource(getDiceImage(diceRoll))
        binding.imageView.contentDescription = diceRoll.toString()

        binding.imageView2.setImageResource(getDiceImage(diceRollTwo))
        binding.imageView2.contentDescription = diceRollTwo.toString()
    }

    private fun getDiceImage(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_0
        }
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int = (1..numSides).random()
}
