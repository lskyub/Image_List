package com.sklim.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sklim.R
import com.sklim.BR
import com.sklim.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding 객체 생성 상속받은 클래스에서 전달 받은 id값을 통해 layout를 생성
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        //binding 객체의 생명주기를 activity에서 관리 하도록 함
        binding.lifecycleOwner = activity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.model, viewModel)
    }
}