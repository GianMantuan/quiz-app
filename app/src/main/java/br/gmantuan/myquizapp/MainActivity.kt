package br.gmantuan.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.gmantuan.myquizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStart.setOnClickListener {
                if (etName.text?.isNotEmpty() == true) {
                    val intent = Intent(binding.root.context, QuizQuestionsActivity::class.java)
                    intent.putExtra(Constants.USER_NAME, etName.text.toString())
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(binding.root.context, "Please enter you name", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}