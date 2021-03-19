package com.scholar.app.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager.widget.ViewPager
import com.scholar.app.MainActivity
import com.scholar.app.R
import com.scholar.app.ScholarshipDetailFragment
import com.scholar.app.databinding.FragmentThirdScreenBinding
import com.scholar.app.onboarding.ViewPagerFragment


class ThirdScreen : Fragment() {
    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.buttonDone.setOnClickListener {
            //TODO this could cause problems check again when testing
            Navigation.findNavController(view!!)
                    .navigate(R.id.action_viewPagerFragment_to_ScholarshipListFragment)
            onBoardingFinished()
        }

        return view
    }

    //TODO add to ulitity package and remove definitions in FirstScreen.kt and SecondScreen.kt
    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}