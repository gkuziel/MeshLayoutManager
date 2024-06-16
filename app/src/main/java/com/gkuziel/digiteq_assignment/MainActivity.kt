package com.gkuziel.digiteq_assignment

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gkuziel.digiteq_assignment.databinding.ActivityMainBinding
import com.gkuziel.digiteq_assignment.uiUtils.ViewState


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCallbacks()
        viewModel.state.observe(this) {
            setState(it)
        }
    }

    private fun initCallbacks() {
        with(binding) {
            radioGroupLayoutManager.setOnCheckedChangeListener { group, checkedId ->
                viewModel.onLayoutManagerChecked(group.indexOfChild(findViewById(checkedId)))
            }
            radioGroupSnapHelper.setOnCheckedChangeListener { group, checkedId ->
                viewModel.onSnapHelperChecked(group.indexOfChild(findViewById(checkedId)))
            }
            switchReversed.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.onReverseLayoutClicked(isChecked)
            }
            btnSmoothScrollToStart.setOnClickListener {}
            btnSmoothScrollToEnd.setOnClickListener {}
            btnSmoothScrollToPosition.setOnClickListener {}
        }
    }

    private fun setState(viewState: ViewState) {
        with(binding) {
            etScrollPosition.setText(viewState.position.toString())
            switchReversed.isChecked = viewState.reversed
            (radioGroupLayoutManager.getChildAt(viewState.layoutManagerType.id) as RadioButton).isChecked =
                true
            (radioGroupSnapHelper.getChildAt(viewState.snapHelperType.id) as RadioButton).isChecked =
                true
        }
        setupRecyclers(viewState)
    }

    private fun setupRecyclers(viewState: ViewState) {
        with(binding) {

        }
    }
}