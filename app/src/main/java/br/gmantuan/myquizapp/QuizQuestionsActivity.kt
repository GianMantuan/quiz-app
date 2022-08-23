package br.gmantuan.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import br.gmantuan.myquizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizQuestionsBinding

    private var currentPosition: Int = 1
    private var questionList: ArrayList<Question>? = null
    private var selectedOption: Int = 0
    private var userName: String? = null
    private var correctAnswers: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(Constants.USER_NAME)
        questionList = Constants.getQuestions()
        setQuestion()
        setListeners()
    }

    private fun setQuestion() {
        with(binding) {
            val question: Question = questionList!![currentPosition - 1]
            defaultOptionView()

            pbQuestion.progress = currentPosition
            tvQuestionItem.text = getString(R.string.question_item, currentPosition.toString())
            tvQuestion.text = question.question
            ivQuizImage.setImageResource(question.image)

            tvOptionOne.text = question.options[0]
            tvOptionTwo.text = question.options[1]
            tvOptionThree.text = question.options[2]
            tvOptionFour.text = question.options[3]

            btnSubmit.visibility =
                if (currentPosition == questionList!!.size) View.VISIBLE else View.GONE

            btnSubmit.text =
                if (currentPosition == questionList!!.size) getString(R.string.finish_quiz) else getString(
                    R.string.next_question
                )
        }
    }

    private fun setListeners() {
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
    }

    private fun defaultOptionView() {
        with(binding) {
            val options = ArrayList<TextView>()

            options.add(0, tvOptionOne)
            options.add(1, tvOptionTwo)
            options.add(2, tvOptionThree)
            options.add(3, tvOptionFour)

            for (option in options) {
                option.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.text_dark_alt
                    )
                )
                option.typeface = Typeface.DEFAULT
                option.background =
                    ContextCompat.getDrawable(root.context, R.drawable.button_pill)
            }
        }

    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()

        selectedOption = selectedOptionNum
        tv.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
        tv.background =
            ContextCompat.getDrawable(binding.root.context, R.drawable.selected_button_pill)

        binding.btnSubmit.visibility = View.VISIBLE

    }

    private fun answer() {
        val question = questionList?.get(currentPosition - 1)

        if (question!!.answer == selectedOption) correctAnswers++

        currentPosition++

        when {
            currentPosition <= questionList!!.size -> {
                setQuestion()
            }
            else -> {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(Constants.USER_NAME, userName)
                intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers)
                intent.putExtra(Constants.TOTAL_QUESTIONS, questionList!!.size)
                startActivity(intent)
                finish()
            }
        }
    }


    override fun onClick(view: View?) {
        with(binding) {
            when (view) {
                tvOptionOne -> selectedOptionView(tvOptionOne, 1)
                tvOptionTwo -> selectedOptionView(tvOptionTwo, 2)
                tvOptionThree -> selectedOptionView(tvOptionThree, 3)
                tvOptionFour -> selectedOptionView(tvOptionFour, 4)
                btnSubmit -> answer()
            }
        }
    }
}