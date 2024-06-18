package com.gkuziel.digiteq_assignment.data

import androidx.lifecycle.MutableLiveData
import com.gkuziel.digiteq_assignment.utils.ColorUtil


class Repository {
    val itemsTop = MutableLiveData(
        MutableList(50) {
            ItemViewModel(
                it,
                ColorUtil.random()
            )
        }
    )
    val itemsBottom = MutableLiveData(
        MutableList(50) {
            ItemViewModel(
                it,
                ColorUtil.random()
            )
        }
    )
}