package com.example.washme.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.washme.R
import com.example.washme.databinding.BottomSheetDialogFragmentFavoritesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FavoritesBottomSheetDialogFragment :
    BottomSheetDialogFragment(R.layout.bottom_sheet_dialog_fragment_favorites) {

    private var binding: BottomSheetDialogFragmentFavoritesBinding? = null
    // TODO own ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding =
            BottomSheetDialogFragmentFavoritesBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }
}