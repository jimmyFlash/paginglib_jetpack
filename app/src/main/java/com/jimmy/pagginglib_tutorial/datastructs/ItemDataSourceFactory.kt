package com.jimmy.pagginglib_tutorial.datastructs

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource


/**
 * use MutableLiveData<> to store our PageKeyedDataSource and for this we need a DataSource.Factory
 */


class ItemDataSourceFactory : DataSource.Factory<Int, Item>() {

    /**
     * PageKeyedDataSource<>
     *  Incremental data loader for page-keyed content, where requests return keys for next/previous pages.
     *  Implement a DataSource using PageKeyedDataSource if you need to use data from page N - 1
     *  to load page N. This is common, for example, in network APIs that include a
     *  next/previous link or key with each page load.
     */

    //creating the mutable live data
    var itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Item>>()

    override fun create(): DataSource<Int, Item> {
        //getting our data source object
        val itemDataSource = ItemDataSource()

        //posting the datasource to get the values in the background
        itemLiveDataSource.postValue(itemDataSource)

        //returning the datasource
        return itemDataSource
    }

}