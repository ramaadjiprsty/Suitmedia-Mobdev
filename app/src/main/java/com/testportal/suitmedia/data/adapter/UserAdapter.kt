package com.testportal.suitmedia.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.testportal.suitmedia.data.remote.response.DataItem
import com.testportal.suitmedia.databinding.UserItemBinding
import com.testportal.suitmedia.views.secondscreen.SecondActivity

class UserAdapter: PagingDataAdapter<DataItem, UserAdapter.ListViewHolder>(DIFF_CALLBACK) {

    class ListViewHolder(private var binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: DataItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataItem.avatar)
                    .into(ivAvatar)
                tvEmail.text = dataItem.email
                tvUsername.text = dataItem.firstName+" "+dataItem.lastName
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, SecondActivity::class.java)
                    intent.putExtra("extra_user", dataItem.firstName+" "+dataItem.lastName)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean = oldItem == newItem
        }
    }
}