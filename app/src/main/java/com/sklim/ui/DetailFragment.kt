package com.sklim.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sklim.R
import com.sklim.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment :
    BindingFragment<FragmentListBinding, DetailViewModel>(R.layout.fragment_detail) {

    override val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.also {
            viewModel.getImage(it)
        }
    }
}