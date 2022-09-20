package com.sklim.ui

import androidx.fragment.app.viewModels
import com.sklim.R
import com.sklim.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BindingFragment<FragmentListBinding, ListViewModel>(R.layout.fragment_list) {

    override val viewModel: ListViewModel by viewModels()
}