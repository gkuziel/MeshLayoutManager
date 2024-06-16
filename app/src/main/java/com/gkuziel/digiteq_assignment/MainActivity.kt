package com.gkuziel.digiteq_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gkuziel.assignment.MeshLayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MeshLayoutManager()
    }
}