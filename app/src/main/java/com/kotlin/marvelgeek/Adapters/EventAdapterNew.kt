package com.kotlin.marvelgeek.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.Entities.EventC
import com.kotlin.marvelgeek.R
import com.squareup.picasso.Picasso

class EventAdapterNew() : RecyclerView.Adapter<EventAdapterNew.ViewHolderEventNew>() {

    var listEvent = ArrayList<EventC>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderEventNew {
        return ViewHolderEventNew(
            LayoutInflater.from(parent.context).inflate(R.layout.item_events, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderEventNew, position: Int) {
        var event: EventC = listEvent[position]

        Picasso.get().load("${event.thumbnail.path}.${event.thumbnail.extension}")
            .fit()
            .into(holder.eventIvImage)
        holder.eventTvTitle.text = event.title
    }

    override fun getItemCount(): Int = listEvent.size

    inner class ViewHolderEventNew(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        var eventIvImage: ImageView = itemView.findViewById(R.id.eventIvImage)
        var eventTvTitle: TextView = itemView.findViewById(R.id.eventTvTitle)
    }

    fun addListEvent(list: ArrayList<EventC>) {
        listEvent = list
        notifyDataSetChanged()
    }
}