package com.sklim.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.sklim.R
import com.sklim.adapter.OnItemClickListener
import com.sklim.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BindingFragment<FragmentListBinding, ListViewModel>(R.layout.fragment_list) {

    override val viewModel: ListViewModel by viewModels()

    var recyclerViewState: Parcelable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewState?.let {
            binding.rvImages.layoutManager?.onRestoreInstanceState(it)
        }
        viewModel.setAdapter(listClickListener)
    }

    private val listClickListener = object : OnItemClickListener {
        override fun onClick(v: View?, position: Int) {
            recyclerViewState = binding.rvImages.layoutManager?.onSaveInstanceState()

            main.navigateSafe(
                R.id.action_navigation_list_to_navigation_detail,
                bundleOf("id" to position)
            )
        }
    }
}