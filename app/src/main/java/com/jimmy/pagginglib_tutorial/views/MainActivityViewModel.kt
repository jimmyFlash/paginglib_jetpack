package com.jimmy.pagginglib_tutorial.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.jimmy.pagginglib_tutorial.datastructs.Item
import com.jimmy.pagginglib_tutorial.datastructs.ItemDataSource
import com.jimmy.pagginglib_tutorial.datastructs.ItemDataSourceFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivityViewModel : ViewModel() {

    //creating livedata for PagedList  and PagedKeyedDataSource
    var itemPagedList: LiveData<PagedList<Item>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Int, Item>>? = null
    var executor: ExecutorService = Executors.newFixedThreadPool(5)

    init {
        Log.e(MainActivityViewModel::class.java.simpleName, "Viewmodel initialized" )
        //getting our data source factory
        val itemDataSourceFactory = ItemDataSourceFactory()

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.itemLiveDataSource

        //Getting PagedList config
        val pagedListConfig :  PagedList.Config =  PagedList.Config.Builder()
            .setEnablePlaceholders(false)
//            .setInitialLoadSizeHint(10)
            .setPageSize( ItemDataSource.PAGE_SIZE)
            .build()

        //Building the paged list
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)
//            .setFetchExecutor(executor)
            .build()
    }

}