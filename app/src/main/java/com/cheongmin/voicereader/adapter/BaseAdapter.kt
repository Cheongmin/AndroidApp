package com.cheongmin.voicereader.adapter

import android.support.v7.widget.RecyclerView

abstract class BaseAdapter<Model, ViewHolder: RecyclerView.ViewHolder?>
    : RecyclerView.Adapter<ViewHolder>() {

    private val items: MutableList<Model> = ArrayList()

    override fun getItemCount(): Int = this.items.size

    fun getItem(index: Int) = this.items.getOrNull(index)
    fun getItems() = this.items

    fun addItem(element: Model) = this.items.add(element)
    fun addItems(elements: List<Model>) =this.items.addAll(elements)

    fun removeItem(element: Model) = this.items.remove(element)
    fun removeItemAt(index: Int) = this.items.removeAt(index)

    fun clear() = this.items.clear()


}