package com.qucoon.rubiesnigeria.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class RecyclerAdapterUtil<T>(
    val context: Context, val itemList: List<T>, val viewHolderLayoutRecourse: Int
) : RecyclerView.Adapter<RecyclerAdapterUtil<T>.ViewHolder>() {
    private var viewList: List<Int> = mutableListOf()
    private var onDataBindListener: ((itemView: View, item: T, position: Int, innerViews: Map<Int, View>) -> Unit)? =
        null
    private var onClickListener: ((item: T, position: Int) -> Unit)? = null
    private var onLongClickListener: ((item: T, position: Int) -> Unit)? = null

    fun addViewsList(viewsList: List<Int>) {
        viewList = viewsList;
    }

    override fun getItemCount(): Int = itemList.size
    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener {
        private var viewMap: MutableMap<Int, View> = mutableMapOf()

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)

            for (view in viewList) {
                viewMap[view] = itemView.findViewById(view)
            }
        }

        fun bindData(position: Int) {
            onDataBindListener?.invoke(itemView, itemList[position], position, viewMap)
        }

        override fun onClick(view: View?) {
            onClickListener?.invoke(itemList[adapterPosition], adapterPosition)
        }

        override fun onLongClick(view: View?): Boolean {
            onLongClickListener?.invoke(itemList[adapterPosition], adapterPosition)
            return true
        }
    }

    fun addOnDataBindListener(listener: (itemView: View, item: T, position: Int, innerViews: Map<Int, View>) -> Unit) {
        onDataBindListener = listener
    }

    fun addOnClickListener(listener: (item: T, position: Int) -> Unit) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(viewHolderLayoutRecourse, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }
}

