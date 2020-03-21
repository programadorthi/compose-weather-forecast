package br.com.programadorthi.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Stack(children = {
                    Background()
                    Box(modifier = LayoutGravity.TopStart + LayoutPadding(top = 24.dp)) {
                        AppBar()
                    }
                })
            }
        }
    }
}
