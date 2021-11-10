package com.ilgar.starexsampleapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ilgar.starexsampleapp.data.models.posts.PostItem
import com.ilgar.starexsampleapp.databinding.PostsItemLayoutBinding

class PostsAdapter: ListAdapter<PostItem, PostsAdapter.PostsViewHolder>(PostsDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val binding = PostsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }



    class PostsViewHolder(private val binding: PostsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemData: PostItem){
            binding.labelPostTitle.text = itemData.title
            binding.labelPostDescription.text = itemData.body
        }
    }


    class PostsDiffUtilCallback: DiffUtil.ItemCallback<PostItem>(){
        override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem == newItem
        }

    }
}