package com.gkuziel.digiteq_assignment

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkuziel.digiteq_assignment.adapter.MyAdapter
import com.gkuziel.digiteq_assignment.data.Data
import com.gkuziel.digiteq_assignment.databinding.ActivityMainBinding
import com.gkuziel.digiteq_assignment.uiUtils.LayoutManagerType
import com.gkuziel.digiteq_assignment.uiUtils.MainViewState


class MainActivity : AppCompatActivity() {

    private val myAdapterTop by lazy { MyAdapter(Data.numbers) }
//    private val myAdapterBottom by lazy { MyAdapter(Data.numbers) }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
        viewModel.state.observe(this) {
            setState(it)
        }
    }

    private fun initLayout() {
        with(binding) {
            recyclerviewTop.adapter = myAdapterTop
//            recyclerviewBottom.adapter = myAdapterBottom
            radioGroupLayoutManager.setOnCheckedChangeListener { group, checkedId ->
                viewModel.onLayoutManagerChecked(group.indexOfChild(findViewById(checkedId)))
            }
            radioGroupSnapHelper.setOnCheckedChangeListener { group, checkedId ->
                viewModel.onSnapHelperChecked(group.indexOfChild(findViewById(checkedId)))
            }
            switchReversed.setOnCheckedChangeListener { buttonView, isChecked ->
                viewModel.onReverseLayoutClicked(isChecked)
            }
            btnSmoothScrollToStart.setOnClickListener {
                recyclerviewTop.smoothScrollToPosition(0)
            }
            btnSmoothScrollToEnd.setOnClickListener {
                recyclerviewTop.smoothScrollToPosition(
                    recyclerviewTop.adapter?.itemCount?.let {
                        it - 1
                    } ?: 0
                )
            }
            btnSmoothScrollToPosition.setOnClickListener {
                recyclerviewTop.smoothScrollToPosition(etScrollPosition.text.toString().toInt())
            }
        }
    }

    private fun setState(viewState: MainViewState) {
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

    private fun setupRecyclers(viewState: MainViewState) {
        with(binding) {
            recyclerviewTop.layoutManager = when (viewState.layoutManagerType) {
                is LayoutManagerType.Mesh ->
                    LinearLayoutManager(
                        this@MainActivity,
                        RecyclerView.HORIZONTAL,
                        false
                    )

                is LayoutManagerType.Linear ->
                    LinearLayoutManager(
                        this@MainActivity,
                        RecyclerView.HORIZONTAL,
                        false
                    )

                is LayoutManagerType.Grid ->
                    GridLayoutManager(
                        this@MainActivity,
                        2,
                        RecyclerView.HORIZONTAL,
                        false
                    )

            }
        }
    }
}