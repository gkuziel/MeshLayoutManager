package com.gkuziel.digiteq_assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gkuziel.digiteq_assignment.data.Repository
import com.gkuziel.digiteq_assignment.data.ItemViewModel
import com.gkuziel.digiteq_assignment.ui.LayoutManagerType
import com.gkuziel.digiteq_assignment.ui.SnapHelperType
import com.gkuziel.digiteq_assignment.ui.MainViewState

class MainViewModel internal constructor(
    private val repository: Repository = Repository()
) : ViewModel() {

    private val initialViewState = MainViewState(
        false,
        LayoutManagerType.Mesh(),
        SnapHelperType.Mesh(),
        30,
        5,
        2
    )

    private val _state = MutableLiveData(initialViewState)

    val state: LiveData<MainViewState>
        get() = _state

    private val _itemsTop = repository.itemsTop
    private val _itemsBottom = repository.itemsBottom

    val itemsTop: LiveData<MutableList<ItemViewModel>>
        get() = _itemsTop

    val itemsBottom: LiveData<MutableList<ItemViewModel>>
        get() = _itemsBottom

    fun onReverseLayoutClicked(isReversed: Boolean) {
        _state.apply {
            if (value?.reversed != isReversed) {
                value = _state.value?.copy(reversed = isReversed)
            }
        }
    }

    fun onLayoutManagerChecked(index: Int) {
        _state.apply {
            if (value?.layoutManagerType?.id != index) {
                value = value?.copy(layoutManagerType = LayoutManagerType.getManager(index))
            }
        }
    }

    fun onSnapHelperChecked(index: Int) {
        _state.apply {
            if (value?.snapHelperType?.id != index) {
                value = value?.copy(snapHelperType = SnapHelperType.getHelper(index))
            }
        }
    }

    fun onDimensionChanged(columns: Int, rows: Int) {
        _state.apply {
            if (value?.columnCount != columns || value?.rowCount != rows) {
                value = value?.copy(columnCount = columns, rowCount = rows)
            }
        }
    }

    fun onScrollToChanged(position: Int) {
        _state.apply {
            value?.position = position
        }
    }
}



