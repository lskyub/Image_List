package com.sklim.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sklim.R
import com.sklim.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BindingFragment<FragmentListBinding, ListViewModel>(R.layout.fragment_list) {

    override val viewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setAdapter(main.listClickListener)
    }
}