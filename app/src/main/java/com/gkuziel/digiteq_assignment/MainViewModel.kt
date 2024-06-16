package com.gkuziel.digiteq_assignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gkuziel.digiteq_assignment.uiUtils.LayoutManagerType
import com.gkuziel.digiteq_assignment.uiUtils.SnapHelperType
import com.gkuziel.digiteq_assignment.uiUtils.MainViewState

class MainViewModel internal constructor(

) : ViewModel() {

    private val initialViewState = MainViewState(
        false,
        LayoutManagerType.Linear(),
        SnapHelperType.None(),
        0
    )

    private val _state = MutableLiveData(initialViewState)

    val state: LiveData<MainViewState>
        get() = _state

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



