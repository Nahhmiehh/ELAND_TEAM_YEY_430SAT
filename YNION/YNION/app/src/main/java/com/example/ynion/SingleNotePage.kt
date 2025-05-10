package com.example.ynion

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ynion.Adapter.CollabAdapterClass
import com.example.ynion.dataclass.CollabDataClass


class SingleNotePage : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<CollabDataClass>
    lateinit var editImage: Array<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_note_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dataInitialize()
        val layoutManager =LinearLayoutManager(this)
        recyclerView = findViewById(R.id.collaboratorsRV)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        dataList = arrayListOf<CollabDataClass>()
        getData()

    }


    private fun dataInitialize() {

        editImage = arrayOf(
            R.drawable.collab_icon,
            R.drawable.collab_icon,
            R.drawable.collab_icon
        )
    }

    private fun  getData() {
        for (i in editImage.indices) {
            val dataClass =
                CollabDataClass(editImage[i])
            dataList.add(dataClass)

        }
        recyclerView.adapter = CollabAdapterClass(dataList)
    }
}