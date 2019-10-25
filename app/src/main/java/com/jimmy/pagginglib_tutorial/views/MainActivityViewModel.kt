package com.jimmy.pagginglib_tutorial.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.jimmy.pagginglib_tutorial.datastructs.Item


class MainActivityViewModel : ViewModel() {

    //creating livedata for PagedList  and PagedKeyedDataSource
    var itemPagedList: LiveData<PagedList<Item>>? = null
    var liveDataSource: LiveData<PageKeyedDataSource<Int, Item>>? = null

}