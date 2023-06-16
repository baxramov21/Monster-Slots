package com.template.slots.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.template.slots.R

class SlotsAdapter : ListAdapter<SlotItem, SlotsAdapter.MyViewHolder>(SlotsItemDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rcv_item, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.webPageLogo.setImageResource(item.imageID)
    }

    inner class MyViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {
        val webPageLogo: ImageView = itemView.findViewById(R.id.image_v_slots_element)
    }
}