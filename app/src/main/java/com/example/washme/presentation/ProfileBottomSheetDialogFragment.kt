package com.example.washme.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.washme.R
import com.example.washme.databinding.BottomSheetDialogFragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileBottomSheetDialogFragment :
    BottomSheetDialogFragment(R.layout.bottom_sheet_dialog_fragment_profile) {

    private var binding: BottomSheetDialogFragmentProfileBinding? = null
//     TODO own ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding =
            BottomSheetDialogFragmentProfileBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }
}