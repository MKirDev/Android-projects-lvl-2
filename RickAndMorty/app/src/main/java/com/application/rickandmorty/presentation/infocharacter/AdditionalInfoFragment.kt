package com.application.rickandmorty.presentation.infocharacter

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.rickandmorty.R
import com.application.rickandmorty.databinding.FragmentAdditionalInfoBinding
import com.bumptech.glide.Glide
import java.util.ArrayList

private const val ARG_PARAM1 = "param1"

class AdditionalInfoFragment : Fragment() {

    private var _binding: FragmentAdditionalInfoBinding? = null
    private val binding get() = _binding!!

    private var listOfItem = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            listOfItem = it?.getStringArrayList(ARG_PARAM1) as ArrayList<String>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            name.text = listOfItem[0]
            Glide
                .with(imageView.context)
                .load(listOfItem[1])
                .into(imageView)

            species.text = listOfItem[2]
            lastKnownLocation.text = listOfItem[3]
            originLocation.text = listOfItem[4]
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}