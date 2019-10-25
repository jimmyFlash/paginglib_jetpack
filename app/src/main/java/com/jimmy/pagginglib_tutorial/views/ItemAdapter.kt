package com.jimmy.pagginglib_tutorial.views

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



class ItemAdapter : PagedListAdapter<Item, ItemAdapter.ItemViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<RecyclerviewUsersBinding>(
            layoutInflater,
            R.layout.recyclerview_users, parent, false
        )

        return ItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ItemViewHolder(var binding: RecyclerviewUsersBinding)
        :RecyclerView.ViewHolder(binding.root){

        lateinit var textView: TextView
        lateinit var  imageView: ImageView

        fun bind(item : Item?){

            //todo nullability check in the layout file
            binding.item = item
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