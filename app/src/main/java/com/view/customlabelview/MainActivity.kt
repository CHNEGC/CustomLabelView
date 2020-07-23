package com.view.customlabelview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.view.labelview.LabelView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<LabelView>(R.id.labelView).apply {
            setSelectedBlock { any, i ->
                Toast.makeText(this@MainActivity, "$any,$i", Toast.LENGTH_SHORT).show()
            }
        }.onCreateLabel(
            listOf(
                "AAAA",
                "BBBB",
                "CCCC",
                "DDDD"
            )
            , listOf(
                "AAAA",
                "BBBB",
                "DDDD"
            )
        )

        val list = List(20) { LabelModel("选项$it") }
        findViewById<LabelView>(R.id.labelView2).apply {
            setSelectedBlock { any, i ->
                Toast.makeText(this@MainActivity, "$any,$i", Toast.LENGTH_SHORT).show()
            }
        }.onCreateLabel(list) {
            return@onCreateLabel (list[it].label)
        }
    }

    data class LabelModel(var label: String)
}