package br.gmantuan.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.gmantuan.myquizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        setListeners()
    }

    private fun initView() {
        with (binding) {
            tvUserName.text = getString(R.string.user_name, intent.getStringExtra(Constants.USER_NAME))
            cpiScoredQuestions.max = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
            cpiScoredQuestions.progress = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)
            tvScoredQuestions.text = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0).toString()
        }
    }

    private fun setListeners() {
        binding.tvExitQuiz.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        with (binding) {
            when (view) {
                tvExitQuiz -> {
                    startActivity(Intent(binding.root.context, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}