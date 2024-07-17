package com.dhruv.quizapplication.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dhruv.quizapplication.R
import com.dhruv.quizapplication.databinding.FragmentResultBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class FragmentResult : Fragment() {

        lateinit var fragmentResultBinding : FragmentResultBinding

        var correctNumber = 0
        var emptyNumber = 0
        var wrongNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentResultBinding = FragmentResultBinding.inflate(layoutInflater,container,false)

        val args = arguments?.let {
            FragmentResultArgs.fromBundle(it)
        }

        args?.let {
            correctNumber= it.correct
            emptyNumber = it.empty
            wrongNumber = it.wrong
        }

        val barEntriesArrayListCorrect =ArrayList<BarEntry>()
        val barEntriesArrayListEmpty =ArrayList<BarEntry>()
        val barEntriesArrayListWrong =ArrayList<BarEntry>()

        barEntriesArrayListCorrect.add(BarEntry(0f,correctNumber.toFloat()))
        barEntriesArrayListEmpty.add(BarEntry(1f,emptyNumber.toFloat()))
        barEntriesArrayListWrong.add(BarEntry(2f,wrongNumber.toFloat()))

        val barDataSetCorrect = BarDataSet(barEntriesArrayListCorrect,"Correct").apply {
            color = Color.GREEN
            valueTextSize = 24F
            setValueTextColors(arrayListOf(Color.BLACK))
        }
        val barDataSetEmpty = BarDataSet(barEntriesArrayListEmpty,"Empty").apply {
            color = Color.YELLOW
            valueTextSize = 24F
            setValueTextColors(arrayListOf(Color.BLACK))
        }
        val barDataSetWrong = BarDataSet(barEntriesArrayListWrong,"Wrong").apply {
            color = Color.RED
            valueTextSize = 24F
            setValueTextColors(arrayListOf(Color.BLACK))
        }

        val barData = BarData(barDataSetCorrect,barDataSetEmpty,barDataSetWrong)

        fragmentResultBinding.resultChart.data = barData

        fragmentResultBinding.buttonNewQuiz.setOnClickListener {

                this.findNavController().popBackStack(R.id.fragmentHome,false)
        }
        fragmentResultBinding.buttonExit.setOnClickListener {

            requireActivity().finish()
        }
        // Inflate the layout for this fragment
        return fragmentResultBinding.root
    }



}