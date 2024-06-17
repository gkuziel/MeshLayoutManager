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
        SnapHelperType.None(),
        0
    )

    private val _state = MutableLiveData(initialViewState)

    val state: LiveData<MainViewState>
        get() = _state

    private val _items = repository.items

    val items: LiveData<List<ItemViewModel>>
        get() = _items

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
}



