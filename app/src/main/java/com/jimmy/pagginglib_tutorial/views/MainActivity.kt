package com.jimmy.pagginglib_tutorial.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimmy.pagginglib_tutorial.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting up recyclerview
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.hasFixedSize()

        val viewmodel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        val itemAdapter = ItemAdapter(this)

        viewmodel.itemPagedList?.observe(this , androidx.lifecycle.Observer {

            //Log.e(MainActivity::class.java.simpleName, "list size: ${it.size}" )

            //in case of any changes
            //submitting the items to adapter
            itemAdapter.submitList(it)

        })

        recyclerview.adapter = itemAdapter
    }
}
