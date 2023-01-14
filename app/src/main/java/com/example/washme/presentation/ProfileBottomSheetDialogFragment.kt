package com.example.washme.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.washme.R
import com.example.washme.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileBottomSheetDialogFragment : BottomSheetDialogFragment(R.layout.fragment_profile) {

    private var binding: FragmentProfileBinding? = null
    // TODO own ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }
}