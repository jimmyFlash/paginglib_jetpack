package com.jimmy.pagginglib_tutorial.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jimmy.pagginglib_tutorial.R
import com.jimmy.pagginglib_tutorial.databinding.RecyclerviewUsersBinding
import com.jimmy.pagginglib_tutorial.datastructs.Item
import android.widget.TextView
import com.bumptech.glide.Glide


class ItemAdapter(val cntxt : Context, var implemeting : CallBacks? = null) :
    PagedListAdapter<Item, ItemAdapter.ItemViewHolder>(REPO_COMPARATOR) {


    interface CallBacks {
        fun  itemCountUpdated(count : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<RecyclerviewUsersBinding>(
            layoutInflater,
            R.layout.recyclerview_users, parent, false
        )

        return ItemViewHolder(binding)

    }

    override fun getItemCount(): Int {

        val count = super.getItemCount()
        Log.e(Item::class.java.simpleName, "The count of items is about $count")

        if(count > 0){
            implemeting?.itemCountUpdated(count)
        }
        return count
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, cntxt)
    }


    class ItemViewHolder(var binding: RecyclerviewUsersBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(item: Item?, cntxt: Context){

            binding.infoCard.icon = cntxt.getDrawable(R.drawable.ic_launcher_background)
            binding.infoCard.title = "Sample title"
            binding.infoCard.content = "Sample text description"

           // Log.e(ItemAdapter::class.java.simpleName, "$item" )
            if(item != null) {
                binding.item = item
                Glide.with(cntxt)
                    .load(item.owner?.profile_image)
                    .into(binding.imageView)
            }
        }

    }

    /**
     * diffUtils implementation for comparator
     */
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.question_id == newItem.question_id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }
}