package com.example.washme.presentation.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.example.washme.R
import com.example.washme.databinding.UserDetailsInfoViewBinding

class UserDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) : LinearLayout(context, attrs) {

    private val binding =
        UserDetailsInfoViewBinding.inflate(LayoutInflater.from(context), this)

    private var header: String = ""
        set(value) {
            field = value
            binding.headerText.text = value
        }

    private var subTitle: String = ""
        set(value) {
            field = value
            binding.subtitleText.text = value
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.UserDetailView) {
            header = getString(R.styleable.UserDetailView_udv_header) ?: ""
            subTitle = getString(R.styleable.UserDetailView_udv_subTitle) ?: ""
        }
    }
}