package com.carles.poi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carles.core.ui.DebounceClickListener
import com.carles.poi.Poi
import com.carles.poi.databinding.ItemPoiListBinding

class PoiListAdapter(onPoiClicked: (Poi) -> Unit) : RecyclerView.Adapter<PoiListAdapter.ViewHolder>() {

    private val items = ArrayList<Poi>()
    private val debouncePoiClickListener = DebounceClickListener(onPoiClicked)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPoiListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.onBindView(items.get(position)) }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<Poi>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPoiListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { debouncePoiClickListener.onClick(items.get(adapterPosition)) }
        }

        fun onBindView(item: Poi) {
            binding.poilistItemTitleTextview.text = item.title
        }
    }
}