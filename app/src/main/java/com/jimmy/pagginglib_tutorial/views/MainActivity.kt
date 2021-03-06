package com.jimmy.pagginglib_tutorial.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jimmy.pagginglib_tutorial.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

// todo handle failed request
// todo check connection to internet
class MainActivity : AppCompatActivity() , ItemAdapter.CallBacks{

    override fun itemCountUpdated(count: Int) {
       if(count > 0 &&  progressCircular.visibility == View.VISIBLE){
           progressCircular.visibility = View.GONE

       }
    }

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting up recyclerview
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.hasFixedSize()

        val viewmodel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        val itemAdapter = ItemAdapter(this, this)

        progressCircular.visibility = View.VISIBLE

        viewmodel.itemPagedList?.observe(this , androidx.lifecycle.Observer {


            //in case of any changes
            //submitting the items to adapter
            itemAdapter.submitList(it)

        })


        recyclerview.adapter = itemAdapter
    }
}
