package com.mertcaliskanyurek.cointicker.data.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class GenericRecyclerAdapter<T> (
    protected var dataList: ArrayList<T>
) : RecyclerView.Adapter<GenericRecyclerAdapter.BaseViewHolder<T>>() {

    var itemClickListener: ItemClickListener<T>? = null

    override fun getItemCount(): Int = dataList.size

    fun changeDataSet(dataList: ArrayList<T>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: T, position: Int) {
        dataList.add(position, item)
        notifyItemInserted(position)
    }

    abstract class BaseViewHolder<T>(val dataBinding: ViewDataBinding)
        : RecyclerView.ViewHolder(dataBinding.root)

    fun interface ItemClickListener<T> {
        fun onItemClick(item: T, position: Int)
    }

}