package com.dhruv.quizapplication.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dhruv.quizapplication.R
import com.dhruv.quizapplication.database.FlagsDao
import com.dhruv.quizapplication.databinding.FragmentQuizBinding
import com.dhruv.quizapplication.model.FlagsModel
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper


class FragmentQuiz : Fragment() {

    lateinit var fragmentQuizbinding : FragmentQuizBinding
    var flagList = ArrayList<FlagsModel>()

    var correctNumber = 0
    var emptyNumber = 0
    var wrongNumber = 0
    var questionNumber = 0

    lateinit var correctFlag : FlagsModel
    var wrongFlags = ArrayList<FlagsModel>()

    val dao = FlagsDao()

    var optionControl = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentQuizbinding = FragmentQuizBinding.inflate(layoutInflater,container,false)


        flagList = dao.getRandomTenRecords(DatabaseCopyHelper(requireActivity()))

        for(flag in flagList){

            Log.d("flags",flag.id.toString())
            Log.d("flags",flag.countryName)
            Log.d("flags",flag.flagName)
            Log.d("flags","******************")
        }

        showData()


        fragmentQuizbinding.buttonA.setOnClickListener {
            answerControl(fragmentQuizbinding.buttonA)
        }
        fragmentQuizbinding.buttonB.setOnClickListener {
            answerControl(fragmentQuizbinding.buttonB)
        }
        fragmentQuizbinding.buttonC.setOnClickListener {
            answerControl(fragmentQuizbinding.buttonC)
        }
        fragmentQuizbinding.buttonD.setOnClickListener {
            answerControl(fragmentQuizbinding.buttonD)
        }
        fragmentQuizbinding.buttonNext.setOnClickListener {
            questionNumber++

            if(questionNumber > 9){

                if(!optionControl){
                    emptyNumber++
                }

                val direction = FragmentQuizDirections.actionFragmentQuizToFragmentResult().apply {

                    correct = correctNumber
                    empty = emptyNumber
                    wrong = wrongNumber
                }

                this.findNavController().apply{
                    navigate(direction)
                    popBackStack(R.id.fragmentResult,false)
                }
            //Toast.makeText(requireActivity(),"Quiz is Completed",Toast.LENGTH_SHORT).show())
            }else{

                showData()

                if(!optionControl){
                    emptyNumber++
                    fragmentQuizbinding.textViewEmpty.text = emptyNumber.toString()
                }else{
                    setButtonToInitialProperties()
                }
            }

            optionControl = false


        }

        // Inflate the layout for this fragment
        return fragmentQuizbinding.root
    }



    private fun showData(){

        fragmentQuizbinding.textViewQuestion.text = resources.getString(R.string.question_number).plus("").plus(questionNumber + 1)

        correctFlag = flagList[questionNumber]

        fragmentQuizbinding.imageViewFlag.setImageResource(resources.getIdentifier(correctFlag.flagName,"drawable",requireActivity().packageName))

        wrongFlags = dao.getRandomThreeRecords(DatabaseCopyHelper(requireActivity()),correctFlag.id)

        val mixoptions = HashSet<FlagsModel>()
        mixoptions.clear()

        mixoptions.add(correctFlag)
        mixoptions.add(wrongFlags[0])
        mixoptions.add(wrongFlags[1])
        mixoptions.add(wrongFlags[2])

        val options = ArrayList<FlagsModel>()
        options.clear()

        for(eachFlag in mixoptions){
            options.add(eachFlag)
        }

        fragmentQuizbinding.buttonA.text = options[0].countryName
        fragmentQuizbinding.buttonB.text = options[1].countryName
        fragmentQuizbinding.buttonC.text = options[2].countryName
        fragmentQuizbinding.buttonD.text = options[3].countryName

    }

    private fun answerControl(button: Button){

        val clickedOption = button.text.toString()
        val correctAnswer = correctFlag.countryName

        if(clickedOption == correctAnswer){

            correctNumber++
            fragmentQuizbinding.textViewCorrect.text = correctNumber.toString()
            button.setBackgroundColor(Color.GREEN)


        }else{

            wrongNumber++
            fragmentQuizbinding.textViewWrong.text = wrongNumber.toString()
            button.setBackgroundColor(Color.RED)
            button.setTextColor(Color.WHITE)

            when(correctAnswer){

                fragmentQuizbinding.buttonA.text -> fragmentQuizbinding.buttonA.setBackgroundColor(Color.GREEN)
                fragmentQuizbinding.buttonB.text -> fragmentQuizbinding.buttonB.setBackgroundColor(Color.GREEN)
                fragmentQuizbinding.buttonC.text -> fragmentQuizbinding.buttonC.setBackgroundColor(Color.GREEN)
                fragmentQuizbinding.buttonD.text -> fragmentQuizbinding.buttonD.setBackgroundColor(Color.GREEN)

            }
        }

        fragmentQuizbinding.buttonA.isClickable = false
        fragmentQuizbinding.buttonB.isClickable = false
        fragmentQuizbinding.buttonC.isClickable = false
        fragmentQuizbinding.buttonD.isClickable = false

        optionControl = true
    }


    private fun setButtonToInitialProperties(){

        fragmentQuizbinding.buttonA.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.black,requireActivity().theme))
            isClickable = true
        }
        fragmentQuizbinding.buttonB.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.black,requireActivity().theme))
            isClickable = true
        }
        fragmentQuizbinding.buttonC.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.black,requireActivity().theme))
            isClickable = true
        }
        fragmentQuizbinding.buttonD.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(resources.getColor(R.color.black,requireActivity().theme))
            isClickable = true
        }
    }
}