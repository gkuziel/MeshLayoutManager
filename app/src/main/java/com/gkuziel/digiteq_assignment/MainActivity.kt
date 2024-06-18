package com.gkuziel.digiteq_assignment

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.gkuziel.assignment.MeshLayoutManager
import com.gkuziel.assignment.MeshSnapHelper
import com.gkuziel.digiteq_assignment.adapter.ItemAdapter
import com.gkuziel.digiteq_assignment.databinding.ActivityMainBinding
import com.gkuziel.digiteq_assignment.ui.LayoutManagerType
import com.gkuziel.digiteq_assignment.ui.MainViewState
import com.gkuziel.digiteq_assignment.ui.SnapHelperType


class MainActivity : AppCompatActivity() {

    private val itemAdapterTop by lazy { ItemAdapter() }
    private val itemAdapterBottom by lazy { ItemAdapter() }

    private val meshSnapHelper by lazy { MeshSnapHelper(readColumnCount(), readRowCount()) }
    private val linearSnapHelper by lazy { LinearSnapHelper() }
    private val pagerSnapHelper by lazy { PagerSnapHelper() }

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
        viewModel.itemsTop.observe(this) {
            itemAdapterTop.setItems(it)
        }
        viewModel.itemsBottom.observe(this) {
            itemAdapterBottom.setItems(it)
        }
    }

    private fun initLayout() {
        with(binding) {
            recyclerviewTop.adapter = itemAdapterTop
            recyclerviewBottom.adapter = itemAdapterBottom

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
                recyclerviewTop.scrollTo(0)
            }
            btnSmoothScrollToEnd.setOnClickListener {
                recyclerviewTop.scrollTo(recyclerviewTop.adapter?.itemCount?.let {
                    it - 1
                } ?: 0)
            }
            btnSmoothScrollToPosition.setOnClickListener {
                recyclerviewTop.scrollTo(
                    etScrollPosition.text.toString().toIntOrNull() ?: 0
                )
            }

            btnAnimateItems.setOnClickListener {
                recyclerviewTop.startLayoutAnimation()
            }
            btnSetMeshDimension.setOnClickListener {
                viewModel.onDimensionChanged(
                    readColumnCount(),
                    readRowCount()
                )
            }
        }
    }

    private fun readColumnCount() = binding.etMeshColumnNumbers.text.toString().toIntOrNull() ?: 5
    private fun readRowCount() = binding.etMeshRowNumbers.text.toString().toIntOrNull() ?: 2

    private fun setState(viewState: MainViewState) {
        with(binding) {
            etScrollPosition.setText(viewState.position.toString())
            switchReversed.isChecked = viewState.reversed
            (radioGroupLayoutManager.getChildAt(viewState.layoutManagerType.id) as? RadioButton)?.isChecked =
                true
            (radioGroupSnapHelper.getChildAt(viewState.snapHelperType.id) as? RadioButton)?.isChecked =
                true
            recyclerviewTop.setLayoutManager(viewState)
            recyclerviewBottom.setLayoutManager(viewState)
            setSnapHelpers(viewState)
        }
    }

    private fun RecyclerView.setLayoutManager(viewState: MainViewState) {
        layoutManager = when (viewState.layoutManagerType) {
            is LayoutManagerType.Mesh ->
                MeshLayoutManager(
                    this@MainActivity,
                    viewState.columnCount,
                    viewState.rowCount,
                    viewState.reversed
                )

            is LayoutManagerType.Linear ->
                LinearLayoutManager(
                    this@MainActivity,
                    RecyclerView.HORIZONTAL,
                    viewState.reversed
                )

            is LayoutManagerType.Grid ->
                GridLayoutManager(
                    this@MainActivity,
                    2,
                    RecyclerView.HORIZONTAL,
                    viewState.reversed
                )

        }
    }

    private fun RecyclerView.scrollTo(position: Int) {
        if (binding.switchSmooth.isChecked) {
            smoothScrollToPosition(position)
        } else {
            scrollToPosition(position)
        }
    }

    private fun setSnapHelpers(viewState: MainViewState) {
        with(binding) {
            detachSnapHelpers()
            when (viewState.snapHelperType) {
                is SnapHelperType.Mesh ->
                    meshSnapHelper.attachToRecyclerView(recyclerviewTop)

                is SnapHelperType.Linear ->
                    linearSnapHelper.attachToRecyclerView(recyclerviewTop)

                is SnapHelperType.Pager ->
                    pagerSnapHelper.attachToRecyclerView(recyclerviewTop)

                is SnapHelperType.None -> {}

            }
        }
    }

    private fun detachSnapHelpers() {
        linearSnapHelper.attachToRecyclerView(null)
        pagerSnapHelper.attachToRecyclerView(null)
        meshSnapHelper.attachToRecyclerView(null)
    }
}