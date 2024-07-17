package com.dhruv.quizapplication.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import com.dhruv.quizapplication.R
import com.dhruv.quizapplication.databinding.FragmentHomeBinding
import com.techmania.flagquizwithsqlitedemo.DatabaseCopyHelper


class FragmentHome : Fragment() {

        lateinit var fragmentHomeBinding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        createAndOpenDatabase()

        fragmentHomeBinding.buttonStart.setOnClickListener {

            val direction = FragmentHomeDirections.actionFragmentHomeToFragmentQuiz()
            this.findNavController().navigate(direction)

        }
        // Inflate the layout for this fragment
        return fragmentHomeBinding.root
    }

    private fun createAndOpenDatabase() {

        try{
            val helper = DatabaseCopyHelper(requireActivity())
            helper.createDataBase()
            helper.openDataBase()

        }catch(e:Exception){

            e.printStackTrace()

        }
    }

}
