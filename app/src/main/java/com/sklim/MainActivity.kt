package com.sklim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import com.sklim.adapter.OnItemClickListener
import com.sklim.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.model = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    val listClickListener = object : OnItemClickListener {
        override fun onClick(v: View?, position: Int) {
            navController.navigateSafe(
                R.id.action_navigation_list_to_navigation_detail,
                bundleOf("id" to position)
            )
        }
    }

    // Extension 메소드를 정의하여 네비게이션 화면이동시 navigate() 대신 아래 메소드를 사용함
    fun NavController.navigateSafe(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navExtras: Navigator.Extras? = null
    ) {
        val action = currentDestination?.getAction(resId) ?: graph.getAction(resId)
        // 현재 fragment의 id와 이동할 fragment의 id가 다르면 화면이동 실행 (같다는 건, 이미 이동이 된 후이기 때문)
        if (action != null && currentDestination?.id != action.destinationId) {
            navigate(resId, args, navOptions, navExtras)
        }
    }
}