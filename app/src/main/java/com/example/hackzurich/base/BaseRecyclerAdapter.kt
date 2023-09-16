package com.example.hackzurich.base

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hackzurich.domain.BaseModel

open class BaseRecyclerAdapter(
    delegates: List<AdapterDelegate>,
    clickToAction: () -> Unit = {}

) : ListAdapter<BaseModel, BaseViewHolder>(BaseDiffUtil()) {

    private val delegateManager = AdapterDelegateManager()

    init {
        delegates.forEach {
            delegateManager.addDelegate(it)
        }
        //delegateManager.addDelegate(EmptySearchDelegate(), EmptyItemHolderDelegate(clickToAction), LoaderPostDelegate(), EmptyListHolderDelegate()
        //) //TODO implement these delegates and remove comments after that
    }

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        delegateManager.getDelegate(viewType).onCreateViewHolder(parent)


    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.d("currentViewHolder", holder.javaClass.name.toString())
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            holder.bindPayload(getItem(position), holder, payloads[0] as MutableList<Any>)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position), holder)
    }

    override fun getItemViewType(position: Int): Int =
        delegateManager.getItemViewType(getItem(position))

    fun getItemByPosition(position: Int) =
        if (position != -1 && position < currentList.size) getItem(position) else null

    fun getItems() = currentList

    override fun getItemCount() = currentList.size

    override fun submitList(list: MutableList<BaseModel>?) {
        synchronized(currentList) {
            super.submitList(list) {
                Handler(Looper.getMainLooper()).post { recyclerView?.invalidateItemDecorations() }
            }
        }
    }

    fun submitItem(item: BaseModel) {
        synchronized(currentList) {
            super.submitList(mutableListOf<BaseModel>().apply { add(item) })
        }
    }

    override fun submitList(list: MutableList<BaseModel>?, commitCallback: Runnable?) {
        synchronized(currentList) {
            super.submitList(list, commitCallback)
        }
    }

    fun isEmpty() = currentList.isEmpty()

    override fun onCurrentListChanged(
        previousList: MutableList<BaseModel>,
        currentList: MutableList<BaseModel>
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }
}