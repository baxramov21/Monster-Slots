package com.template.slots.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class SlotsItemDiffCallback : DiffUtil.ItemCallback<SlotItem>() {

    override fun areItemsTheSame(oldItem: SlotItem, newItem: SlotItem): Boolean {
        return oldItem.imageID == newItem.imageID
    }

    override fun areContentsTheSame(oldItem: SlotItem, newItem: SlotItem): Boolean {
        return oldItem == newItem
    }

}
