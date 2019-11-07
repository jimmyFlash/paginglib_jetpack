package com.jimmy.pagginglib_tutorial.datastructs

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.jimmy.pagginglib_tutorial.networking.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Integer here defines the page key, which is in our case a number or an integer.
 * Every time we want a new page from the API we need to pass the page number that we want which is an integer.
 * Item is the item that we will get from the API or that we want to get. We already have a class named Item.
 *
 * Then we defined the size of a page which is 50, the initial page number which is 1 and the
 * sitename from where we want to fetch the data
 *
 * 3 overridden methods.
 * loadInitials(): This method will load the initial data.
 * loadBefore(): This method will load the previous page.
 * loadAfter(): This method will load the next page.
 *
 * For creating a Data Source we can use ItemKeyedDataSource, PageKeyedDataSource, PositionalDataSource.
 * PageKeyedDataSource, as in our API we need to pass the page number for each page that we want to fetch.
 * So here the page number becomes the Key of our page.
 */
class ItemDataSource : PageKeyedDataSource<Int, Item>() {

    companion object {
        //the size of a page that we want
        val PAGE_SIZE = 50

        //start from the first page which is 1
        private const val FIRST_PAGE = 1

        //we need to fetch from stackoverflow
        private const val SITE_NAME = "stackoverflow"
    }


    // called once to load the initial data, or first page
    override fun loadInitial(params: LoadInitialParams<Int>,
                             callback: LoadInitialCallback<Int, Item>) {

        RetrofitClient.getInstance() // instantiate retrofit
            .getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)// query the answers endpoint
            .enqueue(object : Callback<StackApiResponse>{// asynchronous callback
                override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {
                    //
                }

                override fun onResponse(call: Call<StackApiResponse>,
                                        response: Response<StackApiResponse> ) {



                    if (response.body() != null) {

                        Log.e(ItemDataSource::class.java.simpleName, "loadInitial - results" )
                        callback.onResult(response.body()?.items as MutableList<Item>,
                            null, FIRST_PAGE + 1)
                    }
                }

            })


    }

    //this will load the previous page
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {

        RetrofitClient.getInstance()
            .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackApiResponse>{
                override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {
                    //
                }

                override fun onResponse(call: Call<StackApiResponse>,
                                        response: Response<StackApiResponse> ) {

                    //if the current page is greater than one
                    //we are decrementing the page number
                    //else there is no previous page
                    val adjacentKey :Int? = if (params.key > 1) params.key - 1 else null
                    if (response.body() != null) {

                        Log.e(ItemDataSource::class.java.simpleName, "loadBefore - results" )
                        //passing the loaded data
                        //and the previous page key
                        callback.onResult(response.body()?.items as MutableList<Item>, adjacentKey)
                    }
                }

            })
    }

    //this will load the next page
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {

        RetrofitClient.getInstance()
            .getAnswers(params.key, PAGE_SIZE, SITE_NAME)
            .enqueue(object : Callback<StackApiResponse>{
                override fun onFailure(call: Call<StackApiResponse>, t: Throwable) {
                    //
                }

                override fun onResponse(call: Call<StackApiResponse>,
                                        response: Response<StackApiResponse> ) {

                    if (response.body() != null) {

                        Log.e(ItemDataSource::class.java.simpleName, "loadAfter - results" )
                        //if the response has next page
                        //incrementing the next page number
                        val key: Int? = if (response.body()!!.has_more) params.key + 1 else null
                        //passing the loaded data
                        //and the previous page key
                        callback.onResult(response.body()?.items as MutableList<Item>, key)
                    }
                }

            })
    }

}