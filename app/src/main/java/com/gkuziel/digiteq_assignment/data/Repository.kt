package com.gkuziel.digiteq_assignment.data

import androidx.lifecycle.MutableLiveData
import com.gkuziel.digiteq_assignment.utils.ColorUtil


class Repository {
    val items = MutableLiveData(
        List(50) {
            ItemViewModel(
                it,
                ColorUtil.random()
            )
        }
    )
}