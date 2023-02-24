package com.carles.hyrule.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carles.common.ui.setDebounceClickListener
import com.carles.hyrule.Monster
import com.carles.hyrule.databinding.ItemMonsterBinding

class MonstersAdapter(private val onClick: (Monster) -> Unit) :
    ListAdapter<Monster, MonstersAdapter.MonstersViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<Monster>() {
        override fun areItemsTheSame(oldItem: Monster, newItem: Monster): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Monster, newItem: Monster): Boolean {
            return oldItem == newItem
        }
    }

    inner class MonstersViewHolder(private val binding: ItemMonsterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Monster) {
            binding.root.setDebounceClickListener { onClick(item) }
            binding.monsterTitle.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonstersViewHolder {
        val binding = ItemMonsterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MonstersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MonstersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}